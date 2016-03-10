package fr.unice.polytech.devops.groupe7.mutation;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by user on 10/03/16.
 */
public class MutationApply {
    private String project;
    private List<String> processors;

    private Document pom;

    public MutationApply(String project, String processor)
    {
        this.project = project;
        this.processors = new ArrayList<String>();
        for(String s : processor.split(","))
            if(!processors.contains(s))
                processors.add(s);
        this.pom = null;
    }

    public void apply()
    {
        if(!loadPom())
        {
            System.err.println("Could not load pom.xml for project " + project);
            return;
        }

        applyMutator();
    }

    private boolean loadPom()
    {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            pom = docBuilder.parse(project+"/pom.xml");
        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }

    private void applyMutator()
    {
        Node root = pom.getFirstChild();

        Node result = null;
        if(nodeHasChild(root, "build"))
        {
            Node node = getChild(root, "build");
            if(nodeHasChild(node, "plugins"))
            {
                node = getChild(node, "plugins");
                NodeList list = node.getChildNodes();
                for(int i=0; i<list.getLength(); i++)
                {
                    Node plugin = list.item(i);
                    if(!plugin.getNodeName().equals("plugin"))
                        continue;
                    Node artifact = getChild(plugin, "artifactId");
                    if(artifact.getTextContent().equals("spoon-maven-plugin"))
                    {
                        result = plugin;
                        break;
                    }
                }

                if(result == null)
                {
                    result = pom.createTextNode("plugin");
                    node.appendChild(result);
                }
            }
            else
            {
                Node plug = pom.createElement("plugins");
                result = pom.createTextNode("plugin");
                plug.appendChild(result);
                node.appendChild(plug);
            }
        }
        else
        {
            Node build = pom.createElement("build");
            Node plug = pom.createElement("plugins");
            result = pom.createTextNode("plugin");
            plug.appendChild(result);
            build.appendChild(plug);
            root.appendChild(build);
        }
        try {
            Path path = Paths.get("./", "base_plugin.xml");
            String content = String.join("\n", Files.readAllLines(path, StandardCharsets.UTF_8));

            for(String processor : processors)
                content = content.replace("#PROCESSORS", "#PROCESSORS<processor>fr.unice.polytech.devops.groupe7.processors."
                        + processor + "</processor>\n");

            content = content.replace("#PROCESSORS", "");

            NodeList nl = result.getChildNodes();
            for(int i=0; i<nl.getLength(); i++)
                result.removeChild(nl.item(i));
            result.setTextContent("#PLUGIN");

            try(  PrintWriter out = new PrintWriter(project+"/pom.xml")  ){
                StringWriter writer = new StringWriter();
                StreamResult sr = new StreamResult(writer);
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer transformer = tf.newTransformer();
                DOMSource domSource = new DOMSource(pom);
                transformer.transform(domSource, sr);
                String xml = writer.toString().replace("#PLUGIN", content);
                out.println(xml);
            }
            catch (TransformerException te)
            {
                System.err.println(te.toString());
            }
            catch (FileNotFoundException e)
            {
                System.err.println(e.toString());
            }

        }catch (IOException e)
        {
            System.err.println(e.toString());
        }
    }

    private boolean nodeHasChild(Node node, String child)
    {
        NodeList list = node.getChildNodes();
        for(int i=0; i<list.getLength(); i++)
            if(list.item(i).getNodeName().equals(child))
                return true;
        return false;
    }

    private Node getChild(Node node, String name)
    {
        NodeList list = node.getChildNodes();
        for(int i=0; i<list.getLength(); i++)
            if(list.item(i).getNodeName().equals(name))
                return list.item(i);
        return null;
    }
}

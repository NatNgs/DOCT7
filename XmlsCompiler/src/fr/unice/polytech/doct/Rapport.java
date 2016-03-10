package fr.unice.polytech.doct;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by rhoo on 24/02/16.
 */
public class Rapport {

    //Nombre de mutant qui ont été rejeté par les tests
    private int deadMutant = 0;

    //Nombre de mutant qui ont passé les tests
    private int aliveMutant = 0;

    //Nombre de mutant qui ont passé les tests en fonction du type de mutations
    private Map<String,Integer> acceptedMutation = new HashMap<>();

    //Nombre de mutant qui n'ont pas passé les tests en fonction du type de mutations
    private final Map<String,Integer> refusedMutation = new HashMap<>();

    //Liste des répertoires contenant les resultats de test des differents mutants
    private List<File> dirList = new ArrayList<>();

    //Liste des mutations précise effectué par type
    private Map<String,ArrayList<String>> mutationList = new HashMap<>();

    //On initialise la liste de répertoire a traiter à la construction
    Rapport(File mainDirectory) {
        for (File f : mainDirectory.listFiles())
            if (f.isDirectory())
                dirList.add(f);
    }

    // Cherche dans stringsToSearch la valeur de l'argument passé en paramètre
    // et incrémente le nombre de mutant tué si elle n'est pas nulle.
    private boolean searchAndUpdate(String argument, String[] stringsToSearch) {
        if(stringsToSearch[0].equals(argument)) {
            String[] argumentNumbers = stringsToSearch[1].split("\"");
            if(Integer.valueOf(argumentNumbers[1]) != 0) {
                //Si on trouve une erreur ou un echec dans un test on coupe la fonction
                deadMutant++;
                return true;
            }
        }
            return false;
    }

    public void compile() throws IOException {
        for(File d : dirList)
            analyze(d);
        File xmlOuput = new File("Result.html");

        FileWriter fw = new FileWriter(xmlOuput);
        fw.write("<!DOCTYPE html>\n\n<meta charset=\"UTF-8\">\n\n<html>\n\t<head>\n\t\t<title>Résultat du test par mutation</title>\n\t\t<script src=\"http://code.highcharts.com/highcharts.js\"></script>\n\t\t<script src=\"jquery-1.12.0.min.js\"></script>\n\t</head>\n\n<body>\n");

        fw.write("\t<h2>Nombre de mutant tué: " + deadMutant+"</h2>\n");
        fw.write("\t<h2>Nombre de mutant vivant: " + aliveMutant + "</h2>\n");
        fw.write("\t<h3> Nombre de mutants vivant par type de mutations </h3>\n");
        fw.write("\t<ul>\n");
        for(String key : acceptedMutation.keySet()) {
            fw.write("\t\t<li>Mutation de type " + key + ": " + acceptedMutation.get(key) + "</li>\n");
        }
        fw.write("\t</ul>\n");
        fw.write("\t<h3> Détail des mutations effectuées par type de mutation</h3>\n");
        for(String key : mutationList.keySet()) {
            fw.write("\t<h4>"+key+"</h4>\n\t<ul>\n");
            for(String mutation : mutationList.get(key))
                fw.write("\t\t<li>"+mutation+"</li>\n");
            fw.write("\t</ul>\n");
        }
        fw.write("\t<div id=\"container\" style=\"width:100%; height:400px;\"></div>\n\t<script>\n");

        fw.write("var chart = new Highcharts.Chart({\n" +
                "        chart: {\n" +
                "            type: 'bar',\n" +
                "            renderTo: 'container'\n" +
                "        },\n" +
                "        title: {\n" +
                "            text: 'Resultats des tests par mutation'\n" +
                "        },\n" +
                "        xAxis: {\n" +
                "        categories: [");
        List<String> mutList = new ArrayList<>();
        List<String> refList = new ArrayList<>();
        List<String> accList = new ArrayList<>();
        for(String s : refusedMutation.keySet())
            refList.add(s);
        for(String s : acceptedMutation.keySet())
            accList.add(s);
        for(String s : accList)
            mutList.add(s);
        mutList.removeAll(refList);
        for(String s : refList)
        mutList.add(s);
        fw.write("'" + mutList.get(0) + "'");
        for(int i = 1; i<mutList.size();i++)
            fw.write(", '" + mutList.get(i) + "'");
        fw.write("]\n" +
                "        },\n" +
                "        yAxis: {\n" +
                "            title: {\n" +
                "                text: 'Mutant'\n" +
                "            }\n" +
                "        },\n" +
                "        series: [{\n" +
                "            name: 'Alive',\n" +
                "            data: [");
                getNumber(acceptedMutation,mutList,fw);
                fw.write("]\n" +
                "        }, {\n" +
                "            name: 'Dead',\n" +
                "            data: [");
                getNumber(refusedMutation,mutList,fw);
                fw.write("]\n" +
                "        }]\n" +
                "    }); ");

        fw.write("\t\t</script>");
        fw.write("</body>\n</html>");
        fw.close();
    }

    private void getNumber(Map<String,Integer> mutation, List<String> mutList, FileWriter fw) throws IOException {
        if(mutList.size() == 0)
            fw.write("0");
        else {
            if (mutation.get(mutList.get(0)) == null)
                fw.write("0");
            else
                fw.write(""+mutation.get(mutList.get(0)));
            for (int i = 1; i < mutList.size(); i++) {
                if (mutation.get(mutList.get(i)) == null)
                    fw.write(", 0");
                else
                    fw.write(", " + mutation.get(mutList.get(i)));
            }
        }
    }
    private void analyze(File directory) throws IOException {
        //On récupére la liste des fichiers de resultat du mutant
        File[] fileList = directory.listFiles();
        String mutationType = "";
        ArrayList<String> preciseMutation = new ArrayList<>();
        for(File g : fileList) {
            Scanner scann = new Scanner(g);

            while (scann.hasNextLine()) {
                //Si le fichier est celui qui contient le type de mutation on le recupere
                if (g.getName().equals("mutation")) {
                    mutationType = scann.nextLine();
                    while(scann.hasNextLine())
                        preciseMutation.add(scann.nextLine());
                    break;
                }
                break;
            }
        }
        for(File f : fileList) {
            Scanner scan = new Scanner(f);
            String line;

            while(scan.hasNextLine()) {
                //Sinon on récupère les arguments de la balise <testsuite>
                line = scan.nextLine();
                String[] lineSplit = line.split(" ");
                if(lineSplit[0].equals("<testsuite")) {
                    for(String s : lineSplit) {
                        String[] equalSplit = s.split("=");

                        boolean error = false;
                        if (searchAndUpdate("failures", equalSplit)) error = true;
                        if (searchAndUpdate("errors", equalSplit)) error = true;
                        if(error) {
                            if(refusedMutation.containsKey(mutationType))
                                refusedMutation.put(mutationType,refusedMutation.get(mutationType)+1);
                            else
                                refusedMutation.put(mutationType,1);
                            return;
                        }
                    }
                }
            }
        }
        //Si on a pas trouvé d'erreur ou d'echec on augmente le nombre de mutant non tué par les tests et le type de mutant
        aliveMutant++;

        //On incrémente le compteur de mutation pour ce type de mutation
        if(acceptedMutation.containsKey(mutationType))
            acceptedMutation.put(mutationType,acceptedMutation.get(mutationType)+1);
        else
            acceptedMutation.put(mutationType,1);

        //On stocke la mutation effectué et la ligne où elle a été effectué
        if(mutationList.containsKey(mutationType)) {
            ArrayList<String> prov = mutationList.get(mutationType);
            prov.addAll(preciseMutation);
            mutationList.put(mutationType, prov);
        }
        else {
            ArrayList<String> prov = new ArrayList<>();
            prov.addAll(preciseMutation);
            mutationList.put(mutationType, prov);
        }
        return;
    }

}

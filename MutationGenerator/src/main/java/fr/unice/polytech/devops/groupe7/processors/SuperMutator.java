package fr.unice.polytech.devops.groupe7.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.UnaryOperatorKind;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.cu.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public abstract class SuperMutator extends AbstractProcessor<CtElement> {
	protected boolean isBegin = true;

	protected void infoWriter(CtElement candidate, String post, String prev, String fileName) {
		try {
	        File output = new File("mutation");
	        FileWriter fw = new FileWriter(output);
			if(isBegin)
	        	fw.write(fileName+"\n");
	        SourcePosition sp = candidate.getPosition();
	        fw.write("Change " + prev + " with "+ post + " on line " + sp.getLine() + " on file " + sp.getFile());

	        fw.close();
	    } 
	    catch(Exception e) { e.printStackTrace(); }
	}
}

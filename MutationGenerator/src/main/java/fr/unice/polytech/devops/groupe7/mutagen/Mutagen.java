package fr.unice.polytech.devops.groupe7.mutagen;

import fr.unice.polytech.devops.groupe7.mutagen.mutation.MutationTester;
import org.mdkt.compiler.InMemoryJavaCompiler;
import spoon.Launcher;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.Filter;
import spoon.reflect.visitor.filter.NameFilter;
import spoon.reflect.visitor.filter.TypeFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 25/02/16.
 */
public class Mutagen {

	public static void main(String[] args) throws Exception {

		ArrayList<String> als = new ArrayList<>();
		Collections.addAll(als, args);
		System.out.println("Mutagen "+als.toString().replace("[", "\"").replace("]", "\"").replace(",", " "));

		if(args.length < 2) {
			System.err.println("usage: Mutagen \"srcFolder/\" \"generatedFolder/\"");
			return;
		}

		String sourceFolder = args[0];  // "src/" > fichier à tester
		String outputFolder = args[1];  // "generated/" > fichier mutés

		Launcher l = new Launcher();

		l.addInputResource(sourceFolder);
		l.setSourceOutputDirectory(outputFolder);

		l.buildModel();


		// v TODO > ERROR HERE
		CtClass javaFile = (CtClass) l.getFactory().Package().getRootPackage().getElements(new NameFilter("*.java")).get(0);
		// ^ TODO > ERROR HERE

		// now we apply a transformation
		// we replace "+" by "-"
		for(Object e : javaFile.getElements(new TypeFilter(CtBinaryOperator.class))) {
			CtBinaryOperator op = (CtBinaryOperator)e;
			if (op.getKind()== BinaryOperatorKind.PLUS) {
				op.setKind(BinaryOperatorKind.MINUS);
			}
		}

		l.run();
	}

}

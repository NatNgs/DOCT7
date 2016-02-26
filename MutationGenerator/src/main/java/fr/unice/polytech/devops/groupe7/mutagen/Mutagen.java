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

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 25/02/16.
 */
public class Mutagen {

	public static void main(String[] args) throws Exception {
		String sourceFolder = args[1];  // "src/" > fichier à tester
		String outputFolder = args[2];  // "generated/" > fichier mutés

		Launcher l = new Launcher();

		l.addInputResource(sourceFolder);
		l.setSourceOutputDirectory(outputFolder);

		l.buildModel();


		CtClass javaFile = (CtClass) l.getFactory().Package().getRootPackage().getElements(new NameFilter("*.java")).get(0);

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

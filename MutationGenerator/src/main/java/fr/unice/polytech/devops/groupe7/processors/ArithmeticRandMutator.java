package fr.unice.polytech.devops.groupe7.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtElement;

import java.util.Random;

/**
 * Created by Nathaël N on 04/03/16.
 * Changes '+', '-', '*', '/', '|', '&' and '^'
 * by one of 6 others randomly (1+2+3+4+5+6 should give 1-2^3*4/5&6 for example)
 */
public class ArithmeticRandMutator extends SuperMutator {
	private static final Random r = new Random(420l);
	private static final int CHANCE = 100;    //  % of how many candidates will be processed

	protected static final String mutatorName = "Arithmetic";

	private static final BinaryOperatorKind[] kinds = new BinaryOperatorKind[]{
			BinaryOperatorKind.PLUS,
			BinaryOperatorKind.MINUS,
			BinaryOperatorKind.MUL,
			BinaryOperatorKind.DIV,
			BinaryOperatorKind.BITAND,
			BinaryOperatorKind.BITOR,
			BinaryOperatorKind.BITXOR
	};

	@Override
	public boolean isToBeProcessed(CtElement candidate){
		if(!(candidate instanceof CtBinaryOperator))
			return false;

		BinaryOperatorKind cbo = ((CtBinaryOperator) candidate).getKind();

		for(int i=kinds.length-1; i>=0; i--)
			if(cbo == kinds[i])
				return (r.nextInt(100) < CHANCE); // SELECTOR
		return false;
	}

	@Override
	public void process(CtElement candidate){
		if(!isToBeProcessed(candidate))
			return;
		CtBinaryOperator op = (CtBinaryOperator) candidate;
		BinaryOperatorKind kind = op.getKind();

		do op.setKind(kinds[r.nextInt(kinds.length)]); while(op.getKind() == kind);

		infoWriter(candidate, kind.toString(), op.getKind().toString(), mutatorName);
	}
}
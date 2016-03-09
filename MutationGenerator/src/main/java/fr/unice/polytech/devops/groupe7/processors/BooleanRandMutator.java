package fr.unice.polytech.devops.groupe7.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.declaration.CtElement;

import java.util.Random;

/**
 * Created by Nathaël N on 04/03/16.
 * Changes '+', '-', '*', '/', '|', '&' and '^'
 * by one of 6 others randomly (1+2+3+4+5+6 should give 1-2^3*4/5&6 for example)
 */
public class BooleanRandMutator extends AbstractProcessor<CtElement> {
	private static final Random r = new Random(0l);
	private static final int CHANCE = 30;    //  % of how many candidates will be processed

	private static final BinaryOperatorKind[] kinds = new BinaryOperatorKind[]{
			BinaryOperatorKind.EQ,
			BinaryOperatorKind.GE,
			BinaryOperatorKind.LE,
			BinaryOperatorKind.GT,
			BinaryOperatorKind.NE,
			BinaryOperatorKind.LT
	};

	@Override
	public boolean isToBeProcessed(CtElement candidate){
		if(!(candidate instanceof CtBinaryOperator))
			return false;

		// Selector
		/*if(r.nextInt(100) > CHANCE)
			return false;*/

		BinaryOperatorKind cbo = ((CtBinaryOperator) candidate).getKind();

		for(int i=kinds.length-1; i>=0; i--)
			if(cbo == kinds[i])
				return true;
		return false;
	}

	@Override
	public void process(CtElement candidate){
		if(!isToBeProcessed(candidate))
			return;
		CtBinaryOperator op = (CtBinaryOperator) candidate;
		BinaryOperatorKind kind = op.getKind();

		do op.setKind(kinds[r.nextInt(kinds.length)]); while(op.getKind() == kind);
	}
}
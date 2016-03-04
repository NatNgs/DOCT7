package fr.unice.polytech.devops.groupe7.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtElement;

/**
 * Created by Nathaël N on 04/03/16.
 * Changes '+', '-', '*' and '/' by one of 3 others randomly (1+2+3+4+5+6 should give 1-2*3*4/5-6 for example)
 */
public class ArithmeticRandMutator extends AbstractProcessor<CtElement> {
	@Override
	public boolean isToBeProcessed(CtElement candidate){
		if(!(candidate instanceof CtBinaryOperator))
			return false;

		BinaryOperatorKind cbo = ((CtBinaryOperator) candidate).getKind();

		return cbo == BinaryOperatorKind.PLUS
				|| cbo == BinaryOperatorKind.MINUS
				|| cbo == BinaryOperatorKind.MUL
				|| cbo == BinaryOperatorKind.DIV;
	}

	@Override
	public void process(CtElement candidate){
		if(!isToBeProcessed(candidate)){
			return;
		}
		CtBinaryOperator op = (CtBinaryOperator) candidate;

		BinaryOperatorKind kind = op.getKind();

		do {
			switch((int)(Math.random() * 4)) {
				case 0:
				op.setKind(BinaryOperatorKind.PLUS);
					break;
				case 1:
				op.setKind(BinaryOperatorKind.MINUS);
					break;
				case 2:
				op.setKind(BinaryOperatorKind.MUL);
					break;
				default:
				op.setKind(BinaryOperatorKind.DIV);
			}
		} while(op.getKind() == kind);
	}
}
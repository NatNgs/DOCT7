package fr.unice.polytech.devops.groupe7.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtElement;

/**at
 * Created by user on 04/03/16.
 */
public class YoloMutator extends AbstractProcessor<CtElement> {
	@Override
	public boolean isToBeProcessed(CtElement candidate){
		return candidate instanceof CtBinaryOperator;
	}

	@Override
	public void process(CtElement candidate){
		if(!isToBeProcessed(candidate)){
			return;
		}
		CtBinaryOperator op = (CtBinaryOperator) candidate;
		op.setKind(BinaryOperatorKind.PLUS);
	}
}
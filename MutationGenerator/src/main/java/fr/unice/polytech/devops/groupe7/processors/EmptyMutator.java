package fr.unice.polytech.devops.groupe7.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.UnaryOperatorKind;
import spoon.reflect.declaration.CtElement;

public class EmptyMutator extends AbstractProcessor<CtElement> {
	@Override
	public boolean isToBeProcessed(CtElement candidate){
		return false;
	}
	public void process(CtElement candidate){
		return;
	}
}

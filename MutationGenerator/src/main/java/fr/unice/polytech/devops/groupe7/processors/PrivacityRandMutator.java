package fr.unice.polytech.devops.groupe7.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtModifiable;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.declaration.ModifierKind;

import java.util.Random;

/**
 * Created by Nathaël N on 04/03/16.
 * Changes 'public' 'private' 'protected'
 * by one of 2 others randomly
 */
public class PrivacityRandMutator extends SuperMutator {
	private static final Random r = new Random(42l);
	private static final int CHANCE = 50;    //  % of how many candidates will be processed

	protected static final String mutatorName = "Privacy";

	private static final ModifierKind[] kinds = new ModifierKind[]{
			ModifierKind.PUBLIC,
			ModifierKind.PRIVATE,
			ModifierKind.PROTECTED
	};

	@Override
	public boolean isToBeProcessed(CtElement candidate){
		if(!(candidate instanceof CtModifiable))
			return false;

		CtModifiable cm = (CtModifiable)candidate;
		if(!(cm instanceof CtVariable))
			return false;

		ModifierKind mk = cm.getVisibility();

		for(int i=kinds.length-1; i>=0; i--)
			if(mk == kinds[i])
				return (r.nextInt(100) < CHANCE); // SELECTOR
		return false;
	}


	@Override
	public void process(CtElement candidate){
		if(!isToBeProcessed(candidate))
			return;

		CtModifiable cm = (CtModifiable)candidate;
		ModifierKind mk = cm.getVisibility();

		do cm.setVisibility(kinds[r.nextInt(kinds.length)]); while(cm.getVisibility() == mk);

		infoWriter(candidate, mk.toString(), cm.getVisibility().toString(), mutatorName);
	}
}
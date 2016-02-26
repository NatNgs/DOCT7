package fr.unice.polytech.devops.groupe7.mutagen.bound.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
public @interface Bound {
	double max();
	double min();
}

package fr.unice.polytech.ecoknowledge.domain.model;

import fr.unice.polytech.ecoknowledge.domain.calculator.GoalVisitor;

/**
 * Created by Benjamin on 27/11/2015.
 */
public interface VisitableComponent {
	void accept(GoalVisitor goalVisitor);
}

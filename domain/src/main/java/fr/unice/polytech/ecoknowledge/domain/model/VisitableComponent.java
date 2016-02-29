package fr.unice.polytech.ecoknowledge.domain.model;

/**
 * Created by Benjamin on 27/11/2015.
 */
public interface VisitableComponent {
	void accept(GoalVisitor goalVisitor);
}

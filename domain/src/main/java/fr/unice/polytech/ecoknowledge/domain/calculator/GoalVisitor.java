package fr.unice.polytech.ecoknowledge.domain.calculator;

import fr.unice.polytech.ecoknowledge.domain.model.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.Level;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.StandardCondition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.improve.ImproveCondition;

/**
 * Created by Benjamin on 26/11/2015.
 */
public interface GoalVisitor {

	void visit(Goal goal);
	void visit(Challenge challenge);
	void visit(Level level);
	void visit(StandardCondition condition);
	void visit(ImproveCondition condition);
}

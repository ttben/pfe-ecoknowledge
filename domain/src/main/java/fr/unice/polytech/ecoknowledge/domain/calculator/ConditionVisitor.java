package fr.unice.polytech.ecoknowledge.domain.calculator;

import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.StandardCondition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.improve.ImproveCondition;

/**
 * Created by Benjamin on 26/11/2015.
 */
public interface ConditionVisitor {
	ConditionResult evaluateCondition(StandardCondition condition);
	ConditionResult evaluateCondition(ImproveCondition condition);
}

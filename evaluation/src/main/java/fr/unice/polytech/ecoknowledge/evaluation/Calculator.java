package fr.unice.polytech.ecoknowledge.evaluation;

import fr.unice.polytech.ecoknowledge.business.Challenge;
import fr.unice.polytech.ecoknowledge.business.Level;
import fr.unice.polytech.ecoknowledge.business.conditions.Condition;
import fr.unice.polytech.ecoknowledge.business.conditions.basic.StandardCondition;
import fr.unice.polytech.ecoknowledge.business.conditions.improve.ImproveCondition;

import java.util.List;

public class Calculator {

	private Cache cache;


	public void evaluateChallenge(Challenge challenge) {
		List<Level> conditionList = challenge.getLevels();
	}

	public void evaluateLevel(Level level) {
		List<Condition> conditions = level.getConditionList();

		for(Condition c : conditions) {
			c.accept(this);
		}

	}

	public void evaluateCondition(StandardCondition condition) {
		System.out.println("HAHAHAHA JAVA = NIQUE");
	}

	public void evaluateCondition(ImproveCondition condition) {

	}

}

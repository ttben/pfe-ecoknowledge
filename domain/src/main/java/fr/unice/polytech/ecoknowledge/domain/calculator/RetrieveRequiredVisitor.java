package fr.unice.polytech.ecoknowledge.domain.calculator;

import fr.unice.polytech.ecoknowledge.domain.model.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.Level;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.StandardCondition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.improve.ImproveCondition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RetrieveRequiredVisitor implements GoalVisitor{

	private Map<String, List<Map<String, String>>> result = new HashMap<>();

	@Override
	public Object visit(Goal goal) {
		System.out.println("In Goal visit ");

		return null;
	}

	@Override
	public Object visit(Challenge challenge) {
		System.out.println("In Challenge visit ");
		return null;
	}

	@Override
	public Object visit(Level level) {
		System.out.println("In Level visit ");
		return null;
	}

	@Override
	public Object visit(StandardCondition condition) {
		System.out.println("In StandardCondition visit ");
		return null;
	}

	@Override
	public Object visit(ImproveCondition condition) {
		System.out.println("In ImproveCondition visit ");
		return null;
	}
}

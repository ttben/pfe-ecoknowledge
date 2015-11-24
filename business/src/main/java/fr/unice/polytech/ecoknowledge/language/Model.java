package fr.unice.polytech.ecoknowledge.language;


import fr.unice.polytech.ecoknowledge.language.repositories.BadgeRepository;
import fr.unice.polytech.ecoknowledge.language.repositories.ChallengeRepository;
import fr.unice.polytech.ecoknowledge.language.repositories.GoalRepository;
import fr.unice.polytech.ecoknowledge.language.repositories.UserRepository;

public class Model {

	private BadgeRepository badgeRepository;
	private ChallengeRepository challengeRepository;
	private GoalRepository goalRepository;
	private UserRepository userRepository;

	/**
	 * 
	 * @param data
	 */
	public void createChallenge(Object data) {
		// TODO - implement language.createChallenge
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idGoal
	 */
	public void getGoal(Integer idGoal) {
		// TODO - implement language.getGoal
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idUser
	 */
	public void getGoalsOfUser(Integer idUser) {
		// TODO - implement language.getGoalsOfUser
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idUser
	 * @param badge
	 */
	public void decernBadge(Integer idUser, Badge badge) {
		// TODO - implement language.decernBadge
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idUser
	 * @param goal
	 */
	public void takeUpChallenge(Integer idUser, Goal goal) {
		// TODO - implement language.takeUpChallenge
		throw new UnsupportedOperationException();
	}
}
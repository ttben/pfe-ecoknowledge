package business;


import business.repositories.BadgeRepository;
import business.repositories.ChallengeRepository;
import business.repositories.GoalRepository;
import business.repositories.UserRepository;

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
		// TODO - implement business.createChallenge
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idGoal
	 */
	public void getGoal(Integer idGoal) {
		// TODO - implement business.getGoal
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idUser
	 */
	public void getGoalsOfUser(Integer idUser) {
		// TODO - implement business.getGoalsOfUser
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idUser
	 * @param badge
	 */
	public void decernBadge(Integer idUser, Badge badge) {
		// TODO - implement business.decernBadge
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idUser
	 * @param goal
	 */
	public void takeUpChallenge(Integer idUser, Goal goal) {
		// TODO - implement business.takeUpChallenge
		throw new UnsupportedOperationException();
	}
}
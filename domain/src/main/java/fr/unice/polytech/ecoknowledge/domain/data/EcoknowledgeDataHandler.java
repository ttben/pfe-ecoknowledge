package fr.unice.polytech.ecoknowledge.domain.data;

import fr.unice.polytech.ecoknowledge.domain.data.exceptions.IncoherentDBContentException;
import fr.unice.polytech.ecoknowledge.domain.data.exceptions.NotReadableElementException;
import fr.unice.polytech.ecoknowledge.domain.data.exceptions.NotSavableElementException;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.User;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.exceptions.UserNotFoundException;

import java.util.List;

/**
 * Created by Benjamin on 05/12/2015.
 */
public interface EcoknowledgeDataHandler {
	void store(Challenge challenge) throws NotSavableElementException;
	void store(User user) throws NotSavableElementException;
	void store(Goal goal) throws NotSavableElementException;

	List<Challenge> readAllChallenges() throws IncoherentDBContentException, NotReadableElementException;

	List<Goal> readAllGoalsOfUser(String userID);
	List<Goal> readAllGoals() throws NotReadableElementException, IncoherentDBContentException;

	List<User> readAllUsers() throws IncoherentDBContentException, NotReadableElementException;

	Challenge readChallengeByID(String challengeID) throws NotReadableElementException;
	Goal readGoalByID(String goalID) throws GoalNotFoundException, NotReadableElementException;
	User readUserByID(String userID) throws UserNotFoundException, NotReadableElementException;

	void updateUser(User user) throws NotSavableElementException;
}

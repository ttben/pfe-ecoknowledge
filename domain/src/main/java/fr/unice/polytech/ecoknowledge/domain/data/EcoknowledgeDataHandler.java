package fr.unice.polytech.ecoknowledge.domain.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.data.exceptions.*;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.User;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Challenge;

import java.util.List;

/**
 * Created by Benjamin on 05/12/2015.
 */
public interface EcoknowledgeDataHandler {
	void store(Challenge challenge) throws NotSavableElementException, NotSavableElementException;

	void store(User user) throws NotSavableElementException;

	void store(Goal goal) throws NotSavableElementException;

	List<Challenge> readAllChallenges() throws IncoherentDBContentException, NotReadableElementException, IncoherentDBContentException, NotReadableElementException;

	List<Goal> readAllGoals() throws NotReadableElementException, IncoherentDBContentException;

	List<User> readAllUsers() throws IncoherentDBContentException, NotReadableElementException;

	List<Goal> readAllGoalsOfUser(String userID) throws IncoherentDBContentException, NotReadableElementException;

	Challenge readChallengeByID(String challengeID) throws NotReadableElementException, ChallengeNotFoundException;

	Goal readGoalByID(String goalID) throws GoalNotFoundException, NotReadableElementException;

	User readUserByID(String userID) throws UserNotFoundException, NotReadableElementException;

	JsonObject readGoalResultByID(String goalResultID);

	Goal readGoalByUserAndChallengeIDs(String userID, String challengeID) throws IncoherentDBContentException, NotReadableElementException, GoalNotFoundException;

	JsonObject readGoalResultByGoalID(String goalID) throws GoalNotFoundException, NotReadableElementException, GoalNotFoundException;

	void deleteGoal(Goal goal) throws GoalNotFoundException;

	void deleteChallenge(Challenge challenge) throws ChallengeNotFoundException;

	void deleteUser(User user) throws UserNotFoundException;

	boolean userHasGoal(String userID, String goalID) throws IncoherentDBContentException, NotReadableElementException;

	boolean userHasTakenChallenge(String userID, String challengeID) throws IncoherentDBContentException, NotReadableElementException;

	void updateUser(User user) throws NotSavableElementException;

	void updateGoal(Goal goal) throws NotSavableElementException;

	JsonArray readAllSensorData();
}

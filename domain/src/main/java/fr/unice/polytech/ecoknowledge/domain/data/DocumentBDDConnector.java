package fr.unice.polytech.ecoknowledge.domain.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.exceptions.ChallengeNotFoundException;
import fr.unice.polytech.ecoknowledge.domain.model.exceptions.UserNotFoundException;

public interface DocumentBDDConnector {
	void storeChallenge(JsonObject challengeJsonDescription);
	void storeGoal(JsonObject goalJsonDescription);
	void storeUser(JsonObject userJsonDescription);
	void storeResult(JsonObject resultJsonDescription);

	void updateUser(JsonObject userJsonDescription);
	void updateGoal(JsonObject goal);

	JsonArray findAllChallenges();
	JsonArray findAllGoals();
	JsonArray findAllUsers();

	JsonArray findGoalsOfUser(String userId);

	JsonObject findChallenge(String challengeID);
	JsonObject findGoal(String goalID);
	JsonObject findUser(String userID);

	JsonObject findGoalResult(String goalResultID);

	void deleteGoalByID(String goalID) throws GoalNotFoundException;
	void deleteChallengeByID(String challengeID) throws ChallengeNotFoundException;
	void deleteUserByID(String userID) throws UserNotFoundException;
}

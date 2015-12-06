package fr.unice.polytech.ecoknowledge.domain.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public interface DocumentBDDConnector {
	void storeChallenge(JsonObject challengeJsonDescription);
	void storeGoal(JsonObject goalJsonDescription);
	void storeUser(JsonObject userJsonDescription);

	void updateUser(JsonObject userJsonDescription);

	JsonArray findAllChallenges();
	JsonArray findAllGoals();
	JsonArray findAllUsers();

	JsonArray findGoalsOfUser(String userId);

	JsonObject findChallenge(String challengeID);
	JsonObject findGoal(String goalID);
	JsonObject findUser(String userID);
}

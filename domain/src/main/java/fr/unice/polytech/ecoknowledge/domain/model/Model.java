package fr.unice.polytech.ecoknowledge.domain.model;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.data.DataPersistence;
import fr.unice.polytech.ecoknowledge.domain.views.challenges.ChallengeView;
import fr.unice.polytech.ecoknowledge.domain.views.goals.GoalView;
import fr.unice.polytech.ecoknowledge.domain.views.users.UserView;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Model {

	public JsonObject createChallenge(JsonObject jsonObject) throws IOException {
		JsonObject result = new JsonObject();

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Challenge challenge = (Challenge) objectMapper.readValue(jsonObject.toString(), Challenge.class);

			jsonObject.addProperty("id",""+challenge.getId());
			DataPersistence.store(DataPersistence.Collections.CHALLENGE,jsonObject);

			result = DataPersistence.read(DataPersistence.Collections.CHALLENGE, challenge.getId().toString());
			Challenge newChallenge = (Challenge)objectMapper.readValue(result.toString(), Challenge.class);

		} catch (JsonMappingException | JsonParseException e) {
			e.printStackTrace();
			throw new InvalidParameterException("Can not build condition with specified parameters :\n " + e.getMessage());
		}

		return result;
	}

	public void getGoal(Integer idGoal) {
		// TODO - implement language.getGoal
		throw new UnsupportedOperationException();
	}

	public JsonArray getGoalsInJsonFormat(List<Goal> goals) {
		JsonArray result = new JsonArray();

		for(Goal currentGoal : goals) {
			//	Create JSON required for client
			JsonObject currentChallengeJsonToClient = new GoalView(currentGoal).toJsonForClient();
			result.add(currentChallengeJsonToClient);
		}

		return result;
	}

	public List<Goal> getGoalsOfUser(String userID) throws IOException {
		List<Goal> result = new ArrayList<>();

		List<JsonObject> challengesDescription = DataPersistence.findGoal(userID);

		ObjectMapper objectMapper = new ObjectMapper();

		for(JsonObject currentGoalJsonObject : challengesDescription) {

			//	Create challenge from DB description
			Goal currentGoal = objectMapper.readValue(currentGoalJsonObject.toString(), Goal.class);
			result.add(currentGoal);
		}

		return result;
	}

	public JsonArray getGoalsOfUserInJsonFormat(String idUser) throws IOException {
		return getGoalsInJsonFormat(getGoalsOfUser(idUser));
	}

	public void decernBadge(Integer idUser, Badge badge) {
		// TODO - implement language.decernBadge
		throw new UnsupportedOperationException();
	}

	public List<Challenge> getAllChallenges() throws IOException {
		List<Challenge> result = new ArrayList<>();

		JsonArray challengesDescription = DataPersistence.readAll(DataPersistence.Collections.CHALLENGE);
		ObjectMapper objectMapper = new ObjectMapper();

		for(JsonElement currentChallengeDescription : challengesDescription) {
			//	Convert value
			JsonObject currentChallengeJsonObject = currentChallengeDescription.getAsJsonObject();

			//	Create challenge from DB description
			Challenge currentChallenge = objectMapper.readValue(currentChallengeJsonObject.toString(), Challenge.class);

			result.add(currentChallenge);
		}

		return result;
	}

	public JsonArray getChallengesInJsonFormat(List<Challenge> challenges) {
		JsonArray result = new JsonArray();

		for(Challenge currentChallenge : challenges) {
			//	Create JSON required for client
			JsonObject currentChallengeJsonToClient = new ChallengeView(currentChallenge).toJsonForClient();
			result.add(currentChallengeJsonToClient);
		}

		return result;
	}

	public JsonArray getAllChallengesInJsonFormat() throws IOException {
		JsonArray result = new JsonArray();

		List<Challenge> challenges = getAllChallenges();

		for(Challenge currentChallenge : challenges) {
			//	Create JSON required for client
			JsonObject currentChallengeJsonToClient = new ChallengeView(currentChallenge).toJsonForClient();
			result.add(currentChallengeJsonToClient);
		}

		return result;
	}

	public Goal takeChallenge(JsonObject jsonObject) throws IOException, JsonParseException, JsonMappingException {
		ObjectMapper objectMapper = new ObjectMapper();
		Goal goal = (Goal)objectMapper.readValue(jsonObject.toString(), Goal.class);
		jsonObject.addProperty("id", goal.getId().toString());

		DataPersistence.store(DataPersistence.Collections.GOAL, jsonObject);

		return goal;
	}

	public JsonObject registerUser(JsonObject userJsonDescription) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		User newUser = (User)objectMapper.readValue(userJsonDescription.toString(), User.class);
		userJsonDescription.addProperty("id", newUser.getId().toString());

		DataPersistence.store(DataPersistence.Collections.USER, userJsonDescription);

		return userJsonDescription;
	}

	public JsonObject getUser(String id) throws IOException {
		JsonElement u = DataPersistence.read(DataPersistence.Collections.USER, id);
		System.out.println("U : " + u);
		ObjectMapper objectMapper = new ObjectMapper();
		User user = (User) objectMapper.readValue(u.toString(), User.class);
		UserView uv = new UserView(user);
		return uv.toJsonForClient();
	}

	public JsonArray getAllUsers() throws IOException {
		JsonArray result = new JsonArray();

		JsonArray usersDescription = DataPersistence.readAll(DataPersistence.Collections.USER);
		ObjectMapper objectMapper = new ObjectMapper();

		for(JsonElement e : usersDescription){
			User user = (User) objectMapper.readValue(e.toString(), User.class);
			UserView uv = new UserView(user);
			result.add(uv.toJsonForClient());
		}

		return usersDescription;
	}

	public void deleteAllUsers() {
		DataPersistence.drop(DataPersistence.Collections.USER);
	}

	public void deleteAllChallenges() {
		DataPersistence.drop(DataPersistence.Collections.CHALLENGE);
	}

	public void deleteAChallenge(String challengeId) {
		DataPersistence.drop(DataPersistence.Collections.CHALLENGE, challengeId);
	}

	public Goal getGoal(String userId, String challengeId) throws IOException {
		List<Goal> goals = getGoalsOfUser(userId);
        for (Goal g : goals) {
			if (g.getChallengeDefinition().getId().toString().equals(challengeId)) {
				return g;
			}
		}
		return null;
	}
	public JsonArray getMosaicForUser(String userID) throws IOException {
		JsonArray result = new JsonArray();

		List<Challenge> allChallenges = getAllChallenges();
		List<Challenge> takenChallenges = getTakenChallenges(userID);
		List<Challenge> notTakenChallenges = new ArrayList<>();

		System.out.println("TAKEN CHALLENGES : " + takenChallenges);
		for(Challenge currentChallenge : allChallenges) {
			System.out.println("CHECK IF CONTAINS " + currentChallenge);
			if(!takenChallenges.contains(currentChallenge)) {
				notTakenChallenges.add(currentChallenge);
			}
		}

		System.out.println("SO : C NOT TAKEN : " + notTakenChallenges);
		result.addAll(getChallengesInJsonFormat(notTakenChallenges));
		result.addAll(getGoalsOfUserInJsonFormat(userID));
		System.out.println("GOAL OF USER : " + getGoalsOfUser(userID));

		return result;
	}

	public List<Challenge> getTakenChallenges(String userID) throws IOException {
		List<Goal> goals = getGoalsOfUser(userID);
		List<Challenge> challenges = new ArrayList<>();

		for(Goal currentGoal : goals) {
			challenges.add(currentGoal.getChallengeDefinition());
		}

		return challenges;
	}

	public JsonArray getAllGoals() {
		return DataPersistence.readAll(DataPersistence.Collections.GOAL);
	}

	public void deleteAllGoals() {
		DataPersistence.drop(DataPersistence.Collections.GOAL);
	}
}
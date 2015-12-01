package fr.unice.polytech.ecoknowledge.domain.model;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.data.DataPersistence;
import fr.unice.polytech.ecoknowledge.domain.model.repositories.BadgeRepository;
import fr.unice.polytech.ecoknowledge.domain.model.repositories.UserRepository;
import fr.unice.polytech.ecoknowledge.domain.model.repositories.ChallengeRepository;
import fr.unice.polytech.ecoknowledge.domain.model.repositories.GoalRepository;
import fr.unice.polytech.ecoknowledge.domain.views.challenges.ChallengeView;

import java.io.IOException;

public class Model {

	private BadgeRepository badgeRepository;
	private ChallengeRepository challengeRepository;
	private GoalRepository goalRepository;
	private UserRepository userRepository;


	public void createChallenge(Challenge challenge) {
		// TODO - implement language.createChallenge
		throw new UnsupportedOperationException();
	}

	public void getGoal(Integer idGoal) {
		// TODO - implement language.getGoal
		throw new UnsupportedOperationException();
	}

	public void getGoalsOfUser(Integer idUser) {
		// TODO - implement language.getGoalsOfUser
		throw new UnsupportedOperationException();
	}

	public void decernBadge(Integer idUser, Badge badge) {
		// TODO - implement language.decernBadge
		throw new UnsupportedOperationException();
	}

	public void takeUpChallenge(Integer idUser, Goal goal) {
		// TODO - implement language.takeUpChallenge
		throw new UnsupportedOperationException();
	}

	public JsonArray getAllChallenges() throws IOException {
		JsonArray result = new JsonArray();

		JsonArray challengesDescription = DataPersistence.readAll(DataPersistence.CHALLENGE_COLLECTION);

		ObjectMapper objectMapper = new ObjectMapper();

		for(JsonElement currentChallengeDescription : challengesDescription) {
			//	Convert value
			JsonObject currentChallengeJsonObject = currentChallengeDescription.getAsJsonObject();

			//	Create challenge from DB description
			Challenge currentChallenge = objectMapper.readValue(currentChallengeJsonObject.toString(), Challenge.class);

			//	Create JSON required for client
			JsonObject currentChallengeJsonToClient = new ChallengeView(currentChallenge).toJsonForClient();
			result.add(currentChallengeJsonToClient);
		}

		return result;
	}

	public void takeChallenge(JsonObject jsonObject) throws IOException, JsonParseException, JsonMappingException {
		ObjectMapper objectMapper = new ObjectMapper();
		Goal goal = (Goal)objectMapper.readValue(jsonObject.toString(), Goal.class);
	}

	public JsonObject registerUser(JsonObject userJsonDescription) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		User newUser = (User)objectMapper.readValue(userJsonDescription.toString(), User.class);
		userJsonDescription.addProperty("id", newUser.getId().toString());

		DataPersistence.store(DataPersistence.USER_COLLECTION, userJsonDescription);

		return userJsonDescription;
	}

	public JsonArray getAllUsers() throws IOException {
		JsonArray result = new JsonArray();

		JsonArray usersDescription = DataPersistence.readAll(DataPersistence.USER_COLLECTION);

		return usersDescription;
	}

	public void deleteAllUsers() {
		DataPersistence.drop(DataPersistence.USER_COLLECTION);
	}
}
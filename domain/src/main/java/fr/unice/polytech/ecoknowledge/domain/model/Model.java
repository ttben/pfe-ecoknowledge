package fr.unice.polytech.ecoknowledge.domain.model;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.data.DataPersistence;
import fr.unice.polytech.ecoknowledge.domain.views.challenges.ChallengeView;
import fr.unice.polytech.ecoknowledge.domain.views.users.UserView;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

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

	public List<Goal> getGoalsOfUser(String idUser) throws IOException {
		List<JsonObject> jsons = DataPersistence.findGoal(idUser);
		ArrayList<Goal> goals = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
		for(JsonObject json : jsons){
			goals.add(mapper.readValue(json.toString(), Goal.class));
		}
		return goals;
	}

	public void decernBadge(Integer idUser, Badge badge) {
		// TODO - implement language.decernBadge
		throw new UnsupportedOperationException();
	}

	public JsonArray getAllChallenges() throws IOException {
		JsonArray result = new JsonArray();

		JsonArray challengesDescription = DataPersistence.readAll(DataPersistence.Collections.CHALLENGE);

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

	public JsonObject takeChallenge(JsonObject jsonObject) throws IOException, JsonParseException, JsonMappingException {
		ObjectMapper objectMapper = new ObjectMapper();
		Goal goal = (Goal)objectMapper.readValue(jsonObject.toString(), Goal.class);
		jsonObject.addProperty("id", goal.getId().toString());

		DataPersistence.store(DataPersistence.Collections.GOAL, jsonObject);

		return jsonObject;
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
		for(Goal g : goals){
			if(g.getChallengeDefinition().getId().equals(challengeId)){
				return g;
			}
		}
		return null;
	}
}
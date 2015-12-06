package fr.unice.polytech.ecoknowledge.domain.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.domain.data.exceptions.IncoherentDBContentException;
import fr.unice.polytech.ecoknowledge.domain.data.exceptions.NotReadableElementException;
import fr.unice.polytech.ecoknowledge.domain.data.exceptions.NotSavableElementException;
import fr.unice.polytech.ecoknowledge.domain.data.utils.MongoDBConnector;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.User;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.exceptions.ChallengeNotFoundException;
import fr.unice.polytech.ecoknowledge.domain.model.exceptions.UserNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MongoDBHandler implements EcoknowledgeDataHandler {

	private static MongoDBHandler instance;
	private MongoDBConnector bddConnector;

	private MongoDBHandler() {
		bddConnector = MongoDBConnector.getInstance();
	}

	public static MongoDBHandler getInstance() {
		if (instance == null) {
			instance = new MongoDBHandler();
		}
		return instance;
	}

	@Override
	public void store(Challenge challenge) throws NotSavableElementException {
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			String challengeStrDescription = objectMapper.writeValueAsString(challenge);
			JsonObject challengeJsonDescription = new JsonParser().parse(challengeStrDescription).getAsJsonObject();
			bddConnector.storeChallenge(challengeJsonDescription);

		} catch (JsonProcessingException e) {
			throwNotSavableElementException("Challenge", challenge.getId().toString(), e);
		}
	}

	@Override
	public void store(User user) throws NotSavableElementException {
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			String userStrDescription = objectMapper.writeValueAsString(user);
			JsonObject userJsonDescription = new JsonParser().parse(userStrDescription).getAsJsonObject();
			bddConnector.storeUser(userJsonDescription);

		} catch (JsonProcessingException e) {
			throwNotSavableElementException("User", user.getId().toString(), e);
		}
	}

	@Override
	public void store(Goal goal) throws NotSavableElementException {
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			String goalStrDescription = objectMapper.writeValueAsString(goal);
			JsonObject goalJsonDescription = new JsonParser().parse(goalStrDescription).getAsJsonObject();
			bddConnector.storeGoal(goalJsonDescription);

		} catch (JsonProcessingException e) {
			throwNotSavableElementException("Goal", goal.getId().toString(), e);
		}
	}

	@Override
	public List<Challenge> readAllChallenges() throws IncoherentDBContentException, NotReadableElementException {
		List<Challenge> result = new ArrayList<>();

		ObjectMapper objectMapper = new ObjectMapper();

		JsonArray challengesJsonArray = bddConnector.findAllChallenges();

		for (JsonElement currentChallengeJsonDescription : challengesJsonArray) {
			if (!currentChallengeJsonDescription.isJsonObject()) {
				throw new IncoherentDBContentException("Read all challenges contains not json object");
			}

			String currentChallengeStrDescription = currentChallengeJsonDescription.getAsString();
			try {
				Challenge currentChallenge = (Challenge) objectMapper.readValue(currentChallengeStrDescription, Challenge.class);
				result.add(currentChallenge);
			} catch (IOException e) {
				throwNotReadableElementException("Challenge", currentChallengeStrDescription, e);
			}
		}

		return result;
	}

	@Override
	public List<Goal> readAllGoalsOfUser(String userID) {
		return null;
	}

	@Override
	public List<Goal> readAllGoals() throws NotReadableElementException, IncoherentDBContentException {
		List<Goal> result = new ArrayList<>();

		ObjectMapper objectMapper = new ObjectMapper();

		JsonArray goalsJsonArray = bddConnector.findAllGoals();

		for (JsonElement currentGoalJsonDescription : goalsJsonArray) {
			if (!currentGoalJsonDescription.isJsonObject()) {
				throw new IncoherentDBContentException("Read all goals contains not json object");
			}

			String currentGoalStrDescription = currentGoalJsonDescription.getAsString();
			try {
				Goal currentGoal = (Goal) objectMapper.readValue(currentGoalStrDescription, Goal.class);
				result.add(currentGoal);
			} catch (IOException e) {
				throwNotReadableElementException("Goal", currentGoalStrDescription, e);
			}
		}

		return result;
	}

	@Override
	public List<User> readAllUsers() throws IncoherentDBContentException, NotReadableElementException {
		List<User> result = new ArrayList<>();

		ObjectMapper objectMapper = new ObjectMapper();

		JsonArray usersJsonArray = bddConnector.findAllUsers();

		for (JsonElement currentUserJsonDescription : usersJsonArray) {
			if (!currentUserJsonDescription.isJsonObject()) {
				throw new IncoherentDBContentException("Read all users contains not json object");
			}

			String currentGoalStrDescription = currentUserJsonDescription.getAsString();
			try {
				User currentUser = (User) objectMapper.readValue(currentGoalStrDescription, User.class);
				result.add(currentUser);
			} catch (IOException e) {
				throwNotReadableElementException("User", currentGoalStrDescription, e);
			}
		}

		return result;
	}

	@Override
	public Challenge readChallengeByID(String challengeID) throws NotReadableElementException {
		JsonObject jsonObject = bddConnector.findChallenge(challengeID);

		if (jsonObject == null) {
			String description = challengeID;
			description = description.concat(" not found");
			throw new ChallengeNotFoundException(description);
		}

		ObjectMapper objectMapper = new ObjectMapper();
		Challenge challenge = null;
		try {
			challenge = (Challenge) objectMapper.readValue(jsonObject.toString(), Challenge.class);
		} catch (IOException e) {
			throwNotReadableElementException("Challenge id:".concat(challengeID), jsonObject.toString(), e);
		}
		return challenge;
	}

	@Override
	public Goal readGoalByID(String goalID) throws GoalNotFoundException, NotReadableElementException {
		JsonObject jsonObject = bddConnector.findGoal(goalID);

		if (jsonObject == null) {
			String description = goalID;
			description = description.concat(" not found");
			throw new GoalNotFoundException(description);
		}

		ObjectMapper objectMapper = new ObjectMapper();
		Goal goal = null;
		try {
			goal = (Goal) objectMapper.readValue(jsonObject.toString(), Goal.class);
		} catch (IOException e) {
			throwNotReadableElementException("Goal id:".concat(goalID), jsonObject.toString(), e);
		}
		return goal;
	}

	@Override
	public User readUserByID(String userID) throws UserNotFoundException, NotReadableElementException {
		JsonObject jsonObject = bddConnector.findUser(userID);

		if (jsonObject == null) {
			String description = userID;
			description = description.concat(" not found");
			throw new UserNotFoundException(description);
		}

		ObjectMapper objectMapper = new ObjectMapper();
		User user = null;
		try {
			user = (User) objectMapper.readValue(jsonObject.toString(), User.class);
		} catch (IOException e) {
			throwNotReadableElementException("User id:".concat(userID), jsonObject.toString(), e);
		}
		return user;
	}

	@Override
	public void updateUser(User user) throws NotSavableElementException {
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			String userStrDescription = objectMapper.writeValueAsString(user);
			JsonObject userJsonDescription = new JsonParser().parse(userStrDescription).getAsJsonObject();
			this.bddConnector.updateUser(userJsonDescription);
		} catch (JsonProcessingException e) {
			throwNotSavableElementException("User", user.getId().toString(), e);
		}
	}

	private void throwNotSavableElementException(String elementType, String id, Exception motherCause) throws NotSavableElementException {
		motherCause.printStackTrace();

		String exceptionDescription = elementType;
		exceptionDescription = exceptionDescription.concat(id);
		exceptionDescription = exceptionDescription.concat(" can not be stored");

		throw new NotSavableElementException(exceptionDescription, motherCause);
	}

	private void throwNotReadableElementException(String elementType, String content, Exception motherCause) throws NotReadableElementException {
		motherCause.printStackTrace();

		String exceptionDescription = elementType;
		exceptionDescription = exceptionDescription.concat(content);
		exceptionDescription = exceptionDescription.concat(" can not be read");

		throw new NotReadableElementException(exceptionDescription, motherCause);
	}
}

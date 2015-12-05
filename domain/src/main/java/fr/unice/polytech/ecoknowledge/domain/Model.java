package fr.unice.polytech.ecoknowledge.domain;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.data.DataPersistence;
import fr.unice.polytech.ecoknowledge.domain.calculator.Cache;
import fr.unice.polytech.ecoknowledge.domain.calculator.Calculator;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.User;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Badge;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.time.Clock;
import fr.unice.polytech.ecoknowledge.domain.model.time.Recurrence;
import fr.unice.polytech.ecoknowledge.domain.model.time.TimeBox;
import fr.unice.polytech.ecoknowledge.domain.model.time.TimeSpanGenerator;
import fr.unice.polytech.ecoknowledge.domain.views.challenges.ChallengeView;
import fr.unice.polytech.ecoknowledge.domain.views.goals.GoalView;
import fr.unice.polytech.ecoknowledge.domain.views.users.UserView;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Model {

	private static Model instance;

	private Calculator calculator;

	public Model(Calculator calculator) {
		this.calculator = calculator;
	}

	public Model() {
		this.calculator = new Calculator(Cache.getFakeCache());
	}

	public static Model getInstance() {
		if(instance == null) {
			instance = new Model();
		}

		return instance;
	}

	public JsonObject createChallenge(JsonObject jsonObject) throws IOException {
		JsonObject result = new JsonObject();

		ObjectMapper objectMapper = new ObjectMapper();

		Challenge challenge = (Challenge) objectMapper.readValue(jsonObject.toString(), Challenge.class);
		jsonObject.addProperty("id", "" + challenge.getId());

		DataPersistence.store(DataPersistence.Collections.CHALLENGE, jsonObject);

		return result;
	}

	public JsonArray getGoalsInJsonFormat(List<Goal> goals) {
		JsonArray result = new JsonArray();

		for (Goal currentGoal : goals) {
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

		for (JsonObject currentGoalJsonObject : challengesDescription) {

			//	Create challenge from DB description
			Goal currentGoal = objectMapper.readValue(currentGoalJsonObject.toString(), Goal.class);
			result.add(currentGoal);
		}

		return result;
	}

	public List<Challenge> getAllChallenges() throws IOException {
		List<Challenge> result = new ArrayList<>();

		JsonArray challengesDescription = DataPersistence.readAll(DataPersistence.Collections.CHALLENGE);
		ObjectMapper objectMapper = new ObjectMapper();

		for (JsonElement currentChallengeDescription : challengesDescription) {
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

		for (Challenge currentChallenge : challenges) {
			//	Create JSON required for client
			JsonObject currentChallengeJsonToClient = new ChallengeView(currentChallenge).toJsonForClient();
			result.add(currentChallengeJsonToClient);
		}

		return result;
	}

	public JsonArray getAllChallengesInJsonFormat() throws IOException {
		JsonArray result = new JsonArray();

		List<Challenge> challenges = getAllChallenges();

		for (Challenge currentChallenge : challenges) {
			//	Create JSON required for client
			JsonObject currentChallengeJsonToClient = new ChallengeView(currentChallenge).toJsonForClient();
			result.add(currentChallengeJsonToClient);
		}

		return result;
	}

	public JsonObject takeChallenge(JsonObject jsonObject, TimeBox next) throws IOException, JsonParseException, JsonMappingException {
		ObjectMapper objectMapper = new ObjectMapper();

		//	Build goal with custom deserializer
		Goal goal = (Goal) objectMapper.readValue(jsonObject.toString(), Goal.class);

		Recurrence goalRecurrence = goal.getChallengeDefinition().getRecurrence();
		Clock clock = calculator.getClock();

		//	Generate a timeSpan and set it into the goal
		TimeBox timeSpan;
		if(next != null)
			timeSpan = TimeSpanGenerator.generateNextTimeSpan(goalRecurrence, clock, next);
		else
			timeSpan = TimeSpanGenerator.generateTimeSpan(goalRecurrence, clock);
		goal.setTimeSpan(timeSpan);

		//	Retrieve JSON description of goal (using custom serializer)
		String goalStrDescription = objectMapper.writeValueAsString(goal);
		JsonObject goalToPersist = new JsonParser().parse(goalStrDescription).getAsJsonObject();

		//	Store goal JSON description into DB
		DataPersistence.store(DataPersistence.Collections.GOAL, goalToPersist);

		JsonObject result = calculator.evaluate(goal);


		return result;
	}

	public JsonObject registerUser(JsonObject userJsonDescription) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		User newUser = (User) objectMapper.readValue(userJsonDescription.toString(), User.class);
		userJsonDescription.addProperty("id", newUser.getId().toString());

		DataPersistence.store(DataPersistence.Collections.USER, userJsonDescription);

		return userJsonDescription;
	}

	public JsonObject getUserProfile(String id) throws IOException {
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
		System.out.println("\n\nUSERS DESCRIPTION : " + usersDescription);
		ObjectMapper objectMapper = new ObjectMapper();

		for (JsonElement e : usersDescription) {
			User user = (User) objectMapper.readValue(e.toString(), User.class);
			UserView uv = new UserView(user);
			result.add(uv.toJsonForClient());
		}

		return result;
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

	public JsonObject getGoal(String goalID) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonObject goalJsonDescription = DataPersistence.read(DataPersistence.Collections.GOAL, goalID);
		Goal result = (Goal) objectMapper.readValue(goalJsonDescription.toString(), Goal.class);
		return goalJsonDescription;
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


	public JsonArray getNotTakenChallengesOfUser(String userID) throws IOException {
		JsonArray result = new JsonArray();

		List<Challenge> allChallenges = getAllChallenges();
		List<Challenge> takenChallenges = getTakenChallenges(userID);
		List<Challenge> notTakenChallenges = new ArrayList<>();

		for (Challenge currentChallenge : allChallenges) {
			if (!takenChallenges.contains(currentChallenge)) {
				notTakenChallenges.add(currentChallenge);
			}
		}

		result.addAll(getChallengesInJsonFormat(notTakenChallenges));

		return result;
	}

	public List<Challenge> getTakenChallenges(String userID) throws IOException {
		List<Goal> goals = getGoalsOfUser(userID);
		List<Challenge> challenges = new ArrayList<>();

		for (Goal currentGoal : goals) {
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

	public void giveBadge(Badge bestBadge, String userId) throws IOException {

        JsonObject u = DataPersistence.read(DataPersistence.Collections.USER, userId);
		ObjectMapper mapper = new ObjectMapper();
		User user = mapper.readValue(u.toString(), User.class);

		user.addBadge(bestBadge);
		String userString = mapper.writeValueAsString(user);
		DataPersistence.update(DataPersistence.Collections.USER, user.getId().toString(), userString);
	}

    public void deleteGoal(String goalId) {
        DataPersistence.drop(DataPersistence.Collections.GOAL, goalId);
    }

	public JsonArray getBadgesOfUser(String id) throws IOException {
		JsonElement u = DataPersistence.read(DataPersistence.Collections.USER, id);
		System.out.println("U : " + u);
		ObjectMapper objectMapper = new ObjectMapper();
		User user = (User) objectMapper.readValue(u.toString(), User.class);

		JsonArray result = new JsonArray();

		List<Badge> badges = new ArrayList<>();
		if(user.getBadges() != null) {
			badges.addAll(user.getBadges());
		}

		for(Badge badge : badges) {
			JsonObject badgeJsonDescription = new JsonObject();
			badgeJsonDescription.addProperty("nameChallenge", badge.getName()); // FIXME: 04/12/2015
			badgeJsonDescription.addProperty("nameLevel", badge.getName()); // FIXME: 04/12/2015
			badgeJsonDescription.addProperty("points", badge.getReward());
			badgeJsonDescription.addProperty("numberPossessed", 1); // FIXME: 04/12/2015

			result.add(badgeJsonDescription);
		}

		return result;
	}


	public void evaluateGoalsForUser(String userId) throws IOException, InvalidParameterException {
		for(Goal g : this.getGoalsOfUser(userId)){
			evaluate(g);
		}
	}

	/*
	public JsonArray getMosaicForUser(String userID) throws IOException {
		JsonArray goalResultArray  = evaluateGoalsForUserResult(userID);

		JsonArray notTakenChallengesJsonArray = this.getNotTakenChallengesOfUser(userID);

		System.out.println("\n\n+\tNot taken challenges : " + notTakenChallengesJsonArray);
		System.out.println("\n\n+\tGoal result : " + goalResultArray);

		JsonArray result = new JsonArray();
		result.addAll(goalResultArray);
		result.addAll(notTakenChallengesJsonArray);

		return result;
	}


	public JsonArray getMosaicForUser(String userID) throws IOException {
		JsonArray result = new JsonArray();

		result.addAll(getNotTakenChallengesOfUser(userID));
		result.addAll(getGoalsOfUserInJsonFormat(userID));
		System.out.println("GOAL OF USER : " + getGoalsOfUser(userID));

		return result;
	}
	*/

	public void setTime(DateTime time) {
		this.calculator.getClock().setFakeTime(time);
	}

	public String getTimeDescription() {
		return this.calculator.getClock().getTime().toString(DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss"));
	}

	public JsonArray getGoalsResultOfUserInJsonFormat(String userID) throws IOException {
		JsonArray goalResultArray  = evaluateGoalsForUserResult(userID);


		return goalResultArray;
	}

	public JsonArray getGoalsOfUserInJsonFormat(String idUser) throws IOException {
		return getGoalsInJsonFormat(getGoalsOfUser(idUser));
	}


	public JsonArray evaluateGoalsForUserResult(String userId) throws IOException {
		JsonArray goalResultList = new JsonArray();

		for (Goal g : this.getGoalsOfUser(userId)) {

			JsonObject goalRes = evaluate(g);
			goalResultList.add(goalRes);
		}

		return goalResultList;
	}

	public JsonObject evaluate(Goal g) throws IOException, InvalidParameterException {
		if(g == null)
			throw new InvalidParameterException("Goal doesn't exist");
		return this.calculator.evaluate(g);
	}

	public JsonObject evaluate(String userId, String challengeId) throws IOException, InvalidParameterException {
		return evaluate(getGoal(userId, challengeId));
	}
}
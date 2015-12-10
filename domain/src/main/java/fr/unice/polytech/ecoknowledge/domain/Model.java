package fr.unice.polytech.ecoknowledge.domain;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.calculator.Cache;
import fr.unice.polytech.ecoknowledge.domain.calculator.Calculator;
import fr.unice.polytech.ecoknowledge.domain.data.GoalNotFoundException;
import fr.unice.polytech.ecoknowledge.domain.data.MongoDBHandler;
import fr.unice.polytech.ecoknowledge.domain.data.exceptions.IncoherentDBContentException;
import fr.unice.polytech.ecoknowledge.domain.data.exceptions.NotReadableElementException;
import fr.unice.polytech.ecoknowledge.domain.data.exceptions.NotSavableElementException;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.User;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Badge;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.exceptions.UserNotFoundException;
import fr.unice.polytech.ecoknowledge.domain.model.time.Clock;
import fr.unice.polytech.ecoknowledge.domain.model.time.Recurrence;
import fr.unice.polytech.ecoknowledge.domain.model.time.TimeBox;
import fr.unice.polytech.ecoknowledge.domain.model.time.TimeSpanGenerator;
import fr.unice.polytech.ecoknowledge.domain.views.goals.GoalResult;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Model {

	private static Model instance;

	private Calculator calculator;

	public Model() {
		this.calculator = new Calculator(Cache.getFakeCache());
	}

	public static Model getInstance() {
		if (instance == null) {
			instance = new Model();
		}

		return instance;
	}

	public List<Challenge> getNotTakenChallengesOfUser(String userID) throws IOException, IncoherentDBContentException, NotReadableElementException {
		List<Challenge> challenges = MongoDBHandler.getInstance().readAllChallenges();
		List<Challenge> takenChallenges = getTakenChallenges(userID);

        challenges.removeAll(takenChallenges);

		return challenges;
	}

	public List<Challenge> getTakenChallenges(String userID) throws IOException, IncoherentDBContentException, NotReadableElementException {
		List<Goal> goals = MongoDBHandler.getInstance().readAllGoalsOfUser(userID);
		List<Challenge> challenges = new ArrayList<>();

        for (Goal currentGoal : goals) {
            challenges.add(currentGoal.getChallengeDefinition());
		}

		return challenges;
	}

	public String createUser(JsonObject userJsonDescription) throws NotSavableElementException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		User user = (User) objectMapper.readValue(userJsonDescription.toString(), User.class);
		MongoDBHandler.getInstance().store(user);
		return user.getId().toString();
	}

	public void createChallenge(JsonObject jsonObject) throws IOException, NotSavableElementException {
		ObjectMapper objectMapper = new ObjectMapper();
		Challenge challenge = (Challenge) objectMapper.readValue(jsonObject.toString(), Challenge.class);
		MongoDBHandler.getInstance().store(challenge);
	}

	public GoalResult takeChallenge(JsonObject goalJsonDescription) throws IOException, GoalNotFoundException, UserNotFoundException, NotReadableElementException, NotSavableElementException {
		return this.takeChallenge(goalJsonDescription, null);
	}

	public GoalResult takeChallenge(JsonObject jsonObject, TimeBox next) throws IOException, JsonParseException, JsonMappingException, GoalNotFoundException, NotSavableElementException, UserNotFoundException, NotReadableElementException {
		ObjectMapper objectMapper = new ObjectMapper();

		//	Build goal with custom deserializer
		Goal goal = (Goal) objectMapper.readValue(jsonObject.toString(), Goal.class);

		System.out.println("GOAL???" + goal);

		Recurrence goalRecurrence = goal.getChallengeDefinition().getRecurrence();
		Clock clock = calculator.getClock();

		//	Generate a timeSpan and set it into the goal
		TimeBox timeSpan;
		if (next != null) {
			timeSpan = TimeSpanGenerator.generateNextTimeSpan(goalRecurrence, clock, next);
		} else {
			timeSpan = TimeSpanGenerator.generateTimeSpan(goalRecurrence, clock);
		}

		goal.setTimeSpan(timeSpan);

		GoalResult result = calculator.evaluate(goal);

		MongoDBHandler.getInstance().store(result);
		goal.setGoalResultID(result.getId());

		MongoDBHandler.getInstance().store(goal);

		return result;
	}

	// TODO: 06/12/2015
	// 					badges persistence must include other information like challenge and goal related.
	// 					this following method must be moved into Controller class
	public JsonArray getBadgesOfUser(String id) throws IOException, UserNotFoundException, NotReadableElementException {
		User user = MongoDBHandler.getInstance().readUserByID(id);

		JsonArray result = new JsonArray();

		List<Badge> badges = new ArrayList<>();
		if (user.getBadges() != null) {
			badges.addAll(user.getBadges());
		}

		for (Badge badge : badges) {
			JsonObject badgeJsonDescription = new JsonObject();
			badgeJsonDescription.addProperty("nameChallenge", badge.getName()); // FIXME: 04/12/2015
			badgeJsonDescription.addProperty("nameLevel", badge.getName()); // FIXME: 04/12/2015
			badgeJsonDescription.addProperty("points", badge.getReward());
			badgeJsonDescription.addProperty("numberPossessed", 1); // FIXME: 04/12/2015

			result.add(badgeJsonDescription);

		}

		return result;
	}

	public void giveBadge(Badge bestBadge, String userId) throws IOException, UserNotFoundException, NotReadableElementException, NotSavableElementException {
		User user = MongoDBHandler.getInstance().readUserByID(userId);
		user.addBadge(bestBadge);

		MongoDBHandler.getInstance().updateUser(user);
	}

	public void deleteGoal(Goal goal) throws GoalNotFoundException {
		MongoDBHandler.getInstance().deleteGoal(goal);
	}

	public void setTime(DateTime time) {
		this.calculator.getClock().setFakeTime(time);
	}

	public String getTimeDescription() {
		return this.calculator.getClock().getTime().toString(DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss"));
	}

	public GoalResult evaluate(String userId, String challengeId) throws IOException, InvalidParameterException, IncoherentDBContentException,
			NotReadableElementException, GoalNotFoundException, UserNotFoundException, NotSavableElementException {

		Goal goal = MongoDBHandler.getInstance().readGoalByUserAndChallengeIDs(userId, challengeId);
		GoalResult result = this.calculator.evaluate(goal);

        MongoDBHandler.getInstance().updateGoalResult(result);

		return result;
	}

    public Clock getCalculatorClock(){ // FIXME: 09/12/2015 Pas tres beau
        return calculator.getClock();
    }
}
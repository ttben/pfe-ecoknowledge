package fr.unice.polytech.ecoknowledge.domain;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.calculator.Cache;
import fr.unice.polytech.ecoknowledge.domain.calculator.Calculator;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.Model;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Badge;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Level;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.Condition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression.Expression;
import fr.unice.polytech.ecoknowledge.domain.model.time.TimeBox;
import fr.unice.polytech.ecoknowledge.domain.views.goals.GoalResult;
import org.joda.time.DateTime;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sébastien on 24/11/2015.
 */
public class Controller {

	private static Controller instance;

	private Model model;
	private Calculator calculator;

	private Controller() {
		model = new Model();
		calculator = new Calculator(Cache.getFakeCache());
	}

	public static Controller getInstance() {
		if (instance == null)
			instance = new Controller();
		return instance;
	}

	public JsonObject createUser(JsonObject userJsonDescription) throws IOException {
		return this.model.registerUser(userJsonDescription);
	}

	public JsonObject createChallenge(JsonObject jsonObject) throws InvalidParameterException, IOException {
		return this.model.createChallenge(jsonObject);
	}


    //  TODO delete token : vertical test only
    public JsonObject createExpression(JsonObject jsonObject) throws InvalidParameterException, IOException {
        JsonObject result = new JsonObject();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Expression expression = (Expression)objectMapper.readValue(jsonObject.toString(), Expression.class);
            //result = ChallengePersistence.store(jsonObject);
        } catch (JsonMappingException | JsonParseException e) {
            e.printStackTrace();
            throw new InvalidParameterException("Can not build condition with specified parameters :\n " + e.getMessage());
        }

        return result;
    }


    //  TODO delete token : vertical test only
    public JsonObject createCondition(JsonObject jsonObject) throws InvalidParameterException, IOException {
        JsonObject result = new JsonObject();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Condition condition = (Condition)objectMapper.readValue(jsonObject.toString(), Condition.class);
            //result = ChallengePersistence.store(jsonObject);
        } catch (JsonMappingException | JsonParseException e) {
            e.printStackTrace();
            throw new InvalidParameterException("Can not build condition with specified parameters :\n " + e.getMessage());
        }

        return result;
    }

    //  TODO delete token : vertical test only
	@Deprecated
    public JsonObject createLevel(JsonObject jsonObject) throws InvalidParameterException, IOException {
        JsonObject result = new JsonObject();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Level level = (Level)objectMapper.readValue(jsonObject.toString(), Level.class);
            //result = ChallengePersistence.store(jsonObject);
        } catch (JsonMappingException | JsonParseException e) {
            e.printStackTrace();
            throw new InvalidParameterException("Can not build condition with specified parameters :\n " + e.getMessage());
        }

        return result;
    }

    public JsonArray getAllChallenges() throws IOException {
        return this.model.getAllChallengesInJsonFormat();
    }

    public boolean dropAllChallenges() {
        // TODO: 01/12/2015 ChallengePersistence.drop();

        this.model.deleteAllChallenges();

        return true;
    }

    public boolean dropAChallenge(String challengeId) {
        this.model.deleteAChallenge(challengeId);
        return true;
    }

    public JsonObject createGoal(JsonObject jsonObject, TimeBox next) throws IOException,JsonParseException, JsonMappingException {
        Goal newGoal = this.model.takeChallenge(jsonObject, calculator.getClock(), next);
        JsonObject result = calculator.evaluate(newGoal);
        return result;
    }

    public JsonObject createNextGoal(JsonObject json, TimeBox timeSpan) throws IOException {
        Goal newGoal = this.model.takeChallenge(json, calculator.getClock(), timeSpan);
        JsonObject result = calculator.evaluate(newGoal);
        return result;
    }

    public JsonArray getAllUsers() throws IOException {
        return this.model.getAllUsers();
    }


    public boolean dropAllUsers() {
        this.model.deleteAllUsers();
        return true;
    }



    public JsonObject getUser(String id) throws IOException {
        return this.model.getUser(id);
    }

	public List<GoalResult> evaluateGoalsForUserResult(String userId) throws IOException {
		List<GoalResult> goalResultList = new ArrayList<>();
		for (Goal g : this.model.getGoalsOfUser(userId)) {

			JsonObject goalRes = evaluate(g);
			ObjectMapper mapper = new ObjectMapper();

			GoalResult currentGoalResult = mapper.readValue(goalRes.toString(), GoalResult.class);
			goalResultList.add(currentGoalResult);
		}

		return goalResultList;
	}

    public JsonObject evaluate(Goal g) throws IOException, InvalidParameterException {
        if(g == null)
            throw new InvalidParameterException("Goal doesn't exist");
        return this.calculator.evaluate(g);
    }

    public JsonObject evaluate(String userId, String challengeId) throws IOException, InvalidParameterException {
        return evaluate(model.getGoal(userId, challengeId));
    }

    public void evaluateGoalsForUser(String userId) throws IOException, InvalidParameterException {
        for(Goal g : this.model.getGoalsOfUser(userId)){
            evaluate(g);
        }
    }

    public void giveBadge(Badge bestBadge, String userId) throws IOException {
        this.model.giveBadge(bestBadge, userId);
    }

    public void deleteGoal(String goalId) {
        this.model.deleteGoal(goalId);
    }

	public JsonArray getMosaicForUser(String userID) throws IOException {
		List<GoalResult> goalResultList = evaluateGoalsForUserResult(userID);

		JsonArray goalResultArray = new JsonArray();
		for(GoalResult goalResult : goalResultList) {
			JsonObject goalResultJsonDescription = goalResult.toJsonForClient();
			goalResultArray.add(goalResultJsonDescription);
		}

		JsonArray notTakenChallengesJsonArray = this.model.getNotTakenChallengesOfUser(userID);

		System.out.println("\n\n+\tNot taken challenges : " + notTakenChallengesJsonArray);
		System.out.println("\n\n+\tGoal result : " + goalResultArray);

		JsonArray result = new JsonArray();
		result.addAll(goalResultArray);
		result.addAll(notTakenChallengesJsonArray);

		return result;
	}

	public JsonArray getAllGoals() {
		return this.model.getAllGoals();
	}

	public void dropAllGoals() {
		this.model.deleteAllGoals();
	}

    public Goal getGoals(String userId, String challengeId) throws IOException {
        return model.getGoal(userId, challengeId);
    }

    public void setTime(DateTime time) {
        this.calculator.getClock().setFakeTime(time);
    }
}
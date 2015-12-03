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
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Level;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.Condition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression.Expression;

import java.io.IOException;
import java.security.InvalidParameterException;

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


    /*
	----------------------------------------------------
    TEST
    ----------------------------------------------------
     */

	//  TODO delete token : vertical test only
	@Deprecated
	public JsonObject createExpression(JsonObject jsonObject) throws InvalidParameterException, IOException {
		JsonObject result = new JsonObject();

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Expression expression = (Expression) objectMapper.readValue(jsonObject.toString(), Expression.class);
			//result = ChallengePersistence.store(jsonObject);
		} catch (JsonMappingException | JsonParseException e) {
			e.printStackTrace();
			throw new InvalidParameterException("Can not build condition with specified parameters :\n " + e.getMessage());
		}

		return result;
	}


	//  TODO delete token : vertical test only
	@Deprecated
	public JsonObject createCondition(JsonObject jsonObject) throws InvalidParameterException, IOException {
		JsonObject result = new JsonObject();

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Condition condition = (Condition) objectMapper.readValue(jsonObject.toString(), Condition.class);
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
			Level level = (Level) objectMapper.readValue(jsonObject.toString(), Level.class);
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
		this.model.deleteAllChallenges();

		return true;
	}

	public boolean dropAChallenge(String challengeId) {
		this.model.deleteAChallenge(challengeId);
		return true;
	}

	public JsonObject createGoal(JsonObject jsonObject) throws IOException, JsonParseException, JsonMappingException {
		Goal newGoal = this.model.takeChallenge(jsonObject, calculator.getClock());

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

	public JsonObject evaluate(Goal g) {
		return this.calculator.evaluate(g);
	}

	public JsonObject evaluate(String userId, String challengeId) throws IOException {
		return evaluate(model.getGoal(userId, challengeId));
	}

	public void evaluateGoalsForUser(String userId) throws IOException {
		for (Goal g : this.model.getGoalsOfUser(userId)) {
			evaluate(g);
		}
	}

	public JsonArray getMosaicForUser(String userID) throws IOException {
		evaluateGoalsForUser(userID);
		return this.model.getMosaicForUser(userID);
	}

	public JsonArray getAllGoals() {
		return this.model.getAllGoals();
	}

	public void dropAllGoals() {
		this.model.deleteAllGoals();
	}
}
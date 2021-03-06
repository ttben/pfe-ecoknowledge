package fr.unice.polytech.ecoknowledge.domain;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.data.exceptions.*;
import fr.unice.polytech.ecoknowledge.domain.data.MongoDBHandler;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.SensorExtractor;
import fr.unice.polytech.ecoknowledge.domain.model.SensorNeeds;
import fr.unice.polytech.ecoknowledge.domain.model.User;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Badge;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.exceptions.InvalidGoalTimespanOverChallengeException;
import fr.unice.polytech.ecoknowledge.domain.views.challenges.ChallengeViewList;
import fr.unice.polytech.ecoknowledge.domain.views.users.UserView;
import fr.unice.polytech.ecoknowledge.domain.views.users.UserViewList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

public class Controller {
	private static Controller instance;
	final Logger logger = LogManager.getLogger(Controller.class);

	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

	public String registerUser(JsonObject userJsonDescription) throws IOException, NotSavableElementException {
		return Model.getInstance().createUser(userJsonDescription);
	}

	public JsonObject takeChallenge(JsonObject description) throws IOException, GoalNotFoundException, UserNotFoundException, NotReadableElementException, NotSavableElementException, InvalidGoalTimespanOverChallengeException {
		Goal goal = Model.getInstance().takeChallenge(description);

		SensorExtractor sensorExtractor = new SensorExtractor(goal);
		goal.accept(sensorExtractor);

		List<SensorNeeds> listOfSensorNeeds = sensorExtractor.getSensorNeedsList();
		for(SensorNeeds sensorNeeds : listOfSensorNeeds) {
			System.out.println("Needs :  " + sensorNeeds.getTargetSensor() + " from " + sensorNeeds.getDateStart() + " to " + sensorNeeds.getDateEnd());
			Response response = TrackingRequestSender.POST(sensorNeeds);
			System.out.println("Response received when asked to track " + sensorNeeds.getTargetSensor() + " : " + response);
		}

		JsonObject result = new JsonObject();
		result.addProperty("id", goal.getId().toString());

		logger.info("" + goal.getUser().getId() + " has taken challenge " + goal.getChallengeDefinition().getId() + " and that has created goal " + goal.getId());

		return result;
	}

	public JsonArray getGoalsResultOfUser(String userID) throws IncoherentDBContentException, NotReadableElementException, GoalNotFoundException {
		List<Goal> goalList = MongoDBHandler.getInstance().readAllGoalsOfUser(userID);

		logger.info("Getting goals result of user " + userID);
		logger.info("Goals : " + goalList);

		JsonArray result = new JsonArray();
		for (Goal currentGoal : goalList) {
			//	Handling case when current goal has never been
			//	evaluated by calculator (happens once it its lifetime)
			if(currentGoal.getGoalResultID() == null) {
				logger.warn("No goal result for goal " + currentGoal.getId() + " skipping it ...");
				continue;
			}

			String goalResultStrID = currentGoal.getGoalResultID().toString();

			logger.info("Try to retrieve goal result with ID " + goalResultStrID);

			JsonObject currentGoalResultJsonDescription = MongoDBHandler.getInstance().readGoalResultByID(goalResultStrID);
			result.add(currentGoalResultJsonDescription);
		}

		return result;
	}

	public JsonObject getGoalResult(String goalID) throws GoalNotFoundException, NotReadableElementException {
		return MongoDBHandler.getInstance().readGoalResultByGoalID(goalID);
	}

	public JsonArray getBadgesOfUser(String userID) throws UserNotFoundException, NotReadableElementException {
		User targetUser = MongoDBHandler.getInstance().readUserByID(userID);
		List<Badge> badge = targetUser.getBadges();
		// TODO: 06/12/2015 return badges description
		return null;
	}

	public Challenge createChallenge(JsonObject challengeJsonDescription) throws IOException, NotSavableElementException {
		System.out.println("\nCreating challenge with json : " + challengeJsonDescription);
		return Model.getInstance().createChallenge(challengeJsonDescription);
	}

	public JsonArray getTakenChallengesOfUser(String userID) throws IOException, IncoherentDBContentException, NotReadableElementException {
		List<Challenge> takenChallenges = Model.getInstance().getTakenChallenges(userID);
		ChallengeViewList challengeViewList = new ChallengeViewList(takenChallenges);
		return challengeViewList.toJsonForClient().getAsJsonArray();
	}

	public JsonArray getNotTakenChallengesOfUser(String userID) throws IOException, IncoherentDBContentException, NotReadableElementException {
		List<Challenge> notTakenChallenges = Model.getInstance().getNotTakenChallengesOfUser(userID);
		ChallengeViewList challengeViewList = new ChallengeViewList(notTakenChallenges);
		return challengeViewList.toJsonForClient().getAsJsonArray();
	}

	public JsonArray getAllChallenges() throws IncoherentDBContentException, NotReadableElementException {
		List<Challenge> challengesDescription = MongoDBHandler.getInstance().readAllChallenges();

		ChallengeViewList challengeViewList = new ChallengeViewList(challengesDescription);
		JsonArray result = challengeViewList.toJsonForClient().getAsJsonArray();

		return result;
	}

	public JsonObject getUserProfile(String userID) throws UserNotFoundException, NotReadableElementException {
		User user = MongoDBHandler.getInstance().readUserByID(userID);
		UserView userView = new UserView(user);
		return userView.toJsonForClient();
	}

	public JsonArray getAllUserProfiles() throws IOException, IncoherentDBContentException, NotReadableElementException {
		List<User> usersDescription = MongoDBHandler.getInstance().readAllUsers();

		UserViewList userViewList = new UserViewList(usersDescription);
		JsonArray result = userViewList.toJsonForClient();

		return result;
	}

	public void drop(String dbName) {
		MongoDBHandler.getInstance().dropCollection(dbName);
	}

	public JsonObject getUserId(String mail, String password) throws UserNotFoundException, NotReadableElementException, UserBadPasswordException {
		User user = MongoDBHandler.getInstance().readUserByLogging(mail, password);
		JsonObject idJsonObject = new JsonObject();
		idJsonObject.addProperty("id", user.getId().toString());
		return idJsonObject;
	}
}

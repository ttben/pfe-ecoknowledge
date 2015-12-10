package fr.unice.polytech.ecoknowledge.domain;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
import fr.unice.polytech.ecoknowledge.domain.views.challenges.ChallengeViewList;
import fr.unice.polytech.ecoknowledge.domain.views.goals.GoalResult;
import fr.unice.polytech.ecoknowledge.domain.views.users.UserView;
import fr.unice.polytech.ecoknowledge.domain.views.users.UserViewList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class Controller {
	final Logger logger = LogManager.getLogger(Controller.class);

	private static Controller instance;

	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

	public String registerUser(JsonObject userJsonDescription) throws IOException, NotSavableElementException {
		return Model.getInstance().createUser(userJsonDescription);
	}

	public JsonObject takeChallenge(JsonObject description) throws IOException, GoalNotFoundException, UserNotFoundException, NotReadableElementException, NotSavableElementException {
		GoalResult goalResult = Model.getInstance().takeChallenge(description);
		return goalResult.toJsonForClient();
	}

	public JsonArray getGoalsResultOfUser(String userID) throws IncoherentDBContentException, NotReadableElementException, GoalNotFoundException {
		List<Goal> goalList = MongoDBHandler.getInstance().readAllGoalsOfUser(userID);

		logger.info("Getting goals result of user " + userID);
		logger.info("Goals : " + goalList);

		JsonArray result = new JsonArray();
		for (Goal currentGoal : goalList) {
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

	public void createChallenge(JsonObject challengeJsonDescription) throws IOException, NotSavableElementException {
		System.out.println("\nCreating challenge with json : " + challengeJsonDescription);
		Model.getInstance().createChallenge(challengeJsonDescription);
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

}

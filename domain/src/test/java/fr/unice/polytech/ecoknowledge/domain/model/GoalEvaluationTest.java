package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.data.DataPersistence;
import fr.unice.polytech.ecoknowledge.domain.Controller;
import fr.unice.polytech.ecoknowledge.domain.TestUtils;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Badge;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.time.Clock;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sébastien on 03/12/2015.
 */
public class GoalEvaluationTest {

    private static JsonObject jsonObject = null;
    private static JsonObject jsonObjectUser = null;
    private static JsonObject jsonObjectGoal = null;

    private static String challengeId = "challengeId";
    private static String userId = "userId";
    private static String goalId = "goalId";
    private static Challenge aChallenge = null;
    private static User aUser = null;
    private static Goal aGoal = null;


    @BeforeClass
    public static void loadJsonFile() throws IOException {

        jsonObject = TestUtils.getFakeJson(3);
        ObjectMapper objectMapper = new ObjectMapper();
        aChallenge = (Challenge) objectMapper.readValue(jsonObject.toString(), Challenge.class);
        challengeId = aChallenge.getId() + "";
        challengeId = aChallenge.getId() + "";

        jsonObjectUser = TestUtils.getFakeUser(1);
        aUser = objectMapper.readValue(jsonObjectUser.toString(), User.class);
        userId = aUser.getId() + "";
    }

    @AfterClass
    public static void eraseInDB(){
        DataPersistence.drop(DataPersistence.Collections.CHALLENGE, challengeId);
        DataPersistence.drop(DataPersistence.Collections.USER, userId);
        DataPersistence.drop(DataPersistence.Collections.GOAL, goalId);
    }

    @Test
    public void checkGoalCreationAndEvaluation() throws IOException {

        aGoal = new Goal(null, aChallenge, null, aUser);
        goalId = aGoal.getId().toString();

        jsonObject.addProperty("id", challengeId);
        jsonObjectUser.addProperty("id", userId);
        jsonObjectGoal = new JsonObject();
        jsonObjectGoal.addProperty("user", userId);
        jsonObjectGoal.addProperty("challenge", challengeId);
        jsonObjectGoal.addProperty("id", goalId);

        DataPersistence.store(DataPersistence.Collections.CHALLENGE, jsonObject);
        DataPersistence.store(DataPersistence.Collections.USER, jsonObjectUser);


        Controller.getInstance().createGoal(jsonObjectGoal);

        JsonObject user = DataPersistence.read(DataPersistence.Collections.USER, userId);
        ObjectMapper mapper = new ObjectMapper();
        User u = mapper.readValue(user.toString(), User.class);

        Badge toCompare = new Badge("http://www.derp.com", 69, "you're too good for me");

        assertTrue(u.getBadges().contains(toCompare));
        assertEquals(1, u.getBadges().size());

    }
}

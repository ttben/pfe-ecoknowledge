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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

// FIXME: 04/12/2015 THIS TEST DUMP A GOAL IN THE BDD WITHOUT ERASING IT
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

        aGoal = Controller.getInstance().getGoals(userId, challengeId);
        System.out.println(aGoal);
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


        Controller.getInstance().createGoal(jsonObjectGoal, null);

        JsonObject user = DataPersistence.read(DataPersistence.Collections.USER, userId);
        ObjectMapper mapper = new ObjectMapper();
        User u = mapper.readValue(user.toString(), User.class);

        assertNull(u.getBadges());

        Controller.getInstance().setTime(DateTime.now().plusDays(1));
        Controller.getInstance().evaluate(userId, challengeId);

        user = DataPersistence.read(DataPersistence.Collections.USER, userId);
        mapper = new ObjectMapper();
        u = mapper.readValue(user.toString(), User.class);

        assertEquals(1, u.getBadges().size());

        Badge toCompare = new Badge("http://www.derp.com", 69, "you're too good for me");
        assertTrue(u.getBadges().contains(toCompare));


        Controller.getInstance().setTime(DateTime.now().plusDays(2));
        Controller.getInstance().evaluate(userId, challengeId);

        user = DataPersistence.read(DataPersistence.Collections.USER, userId);
        mapper = new ObjectMapper();
        u = mapper.readValue(user.toString(), User.class);

        assertEquals(2, u.getBadges().size());

        DataPersistence.drop(DataPersistence.Collections.GOAL, jsonObjectGoal.get("id").getAsString());
        DataPersistence.drop(DataPersistence.Collections.CHALLENGE, jsonObject.get("id").getAsString());
        DataPersistence.drop(DataPersistence.Collections.USER, jsonObjectUser.get("id").getAsString());
    }
}

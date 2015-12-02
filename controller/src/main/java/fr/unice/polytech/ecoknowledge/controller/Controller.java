package fr.unice.polytech.ecoknowledge.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.data.DataPersistence;
import fr.unice.polytech.ecoknowledge.domain.model.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.Level;
import fr.unice.polytech.ecoknowledge.domain.model.Model;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.Condition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression.Expression;
import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidParameterException;

/**
 * Created by Sébastien on 24/11/2015.
 */
public class Controller {

    private static Controller instance;

    private Model model;

    private Controller() {
        model = new Model();
    }

    public static Controller getInstance() {
        if(instance == null)
            instance = new Controller();
        return instance;
    }

    public JsonObject createUser(JsonObject userJsonDescription) throws IOException {
        return this.model.registerUser(userJsonDescription);
    }

    public JsonObject createChallenge(JsonObject jsonObject)throws InvalidParameterException, IOException {
        return this.model.createChallenge(jsonObject);
    }


    /*
    ----------------------------------------------------
    TEST
    ----------------------------------------------------
     */

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
        return this.model.getAllChallenges();
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

    public void createGoal(JsonObject jsonObject) throws IOException,JsonParseException, JsonMappingException {
        this.model.takeChallenge(jsonObject);
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
}

package fr.unice.polytech.ecoknowledge.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
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


    public JsonObject createChallenge(JsonObject jsonObject)throws InvalidParameterException, IOException {
        JsonObject result = new JsonObject();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Challenge challenge = (Challenge) objectMapper.readValue(jsonObject.toString(), Challenge.class);
            //result = ChallengePersistance.store(jsonObject);
        } catch (JsonMappingException | JsonParseException e) {
            e.printStackTrace();
            throw new InvalidParameterException("Can not build condition with specified parameters :\n " + e.getMessage());
        }

        return result;
    }

    public JSONObject createBadge(JSONObject json) {

        // Check structure with model
        // TODO

        // Store the badge in the data module
        //return BadgePersistance.store(json);
        JSONObject js = new JSONObject();
        js.put("valid", true);
        return js;

    }

    public JSONObject searchBadge(String challengeId) {
        return new JSONObject();
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
            //result = ChallengePersistance.store(jsonObject);
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
            //result = ChallengePersistance.store(jsonObject);
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
            //result = ChallengePersistance.store(jsonObject);
        } catch (JsonMappingException | JsonParseException e) {
            e.printStackTrace();
            throw new InvalidParameterException("Can not build condition with specified parameters :\n " + e.getMessage());
        }

        return result;
    }
}

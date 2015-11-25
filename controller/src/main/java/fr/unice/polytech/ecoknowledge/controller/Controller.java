package fr.unice.polytech.ecoknowledge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.ChallengePersistance;
import fr.unice.polytech.ecoknowledge.language.Challenge;
import fr.unice.polytech.ecoknowledge.language.Model;
import org.json.JSONObject;

import java.io.IOException;

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

    public JsonObject createChallenge(JsonObject jsonObject) {

        JsonObject result = new JsonObject();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Challenge challenge = (Challenge)objectMapper.readValue(jsonObject.toString(), Challenge.class);
            result = ChallengePersistance.store(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
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

    public JSONObject searchBadge() {
        return new JSONObject();
    }
}

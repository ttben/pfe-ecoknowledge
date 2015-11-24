package fr.unice.polytech.ecoknowledge.controller;

import org.json.JSONObject;

/**
 * Created by Sébastien on 24/11/2015.
 */
public class Controller {

    private static Controller instance;

    private Controller() {}

    public static Controller getInstance() {
        if(instance == null)
            instance = new Controller();
        return instance;
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

package fr.unice.polytech.ecoknowledge.language.api.implem.util;

import junit.framework.Assert;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sébastien on 30/11/2015.
 */
public class JsonSearcher {

    public static Object lookFor(JSONObject json, List<Map.Entry<Object, Class>> path){

        Object current = json;

        for (Map.Entry<Object, Class> c : path) {
            if (c.getKey().getClass().equals(Integer.class)) {
                assertEquals(JSONArray.class, current.getClass());
                JSONArray jsa = (JSONArray) current;
                current = jsa.get((int) c.getKey());

                assertEquals(current.getClass(), c.getValue());

            } else if (c.getKey().getClass().equals(String.class)) {
                assertEquals(JSONObject.class, current.getClass());
                JSONObject jso = (JSONObject) current;
                current = jso.get((String) c.getKey());

                assertEquals(c.getValue(), current.getClass());

            } else {
                return null;
            }
        }

        return current;
    }

}

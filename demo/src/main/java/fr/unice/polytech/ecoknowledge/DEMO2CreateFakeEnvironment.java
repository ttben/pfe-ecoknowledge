package fr.unice.polytech.ecoknowledge;

import com.google.gson.JsonObject;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Sébastien on 13/12/2015.
 */
public class DEMO2CreateFakeEnvironment {

    public static void main(String[] args){


        JsonObject date = new JsonObject();
        date.addProperty("date", "2015-12-14T12:00:00Z");

        POST("http://localhost:8080/Ecoknowledge/", "test/clock",
                Entity.entity(date.toString(), MediaType.APPLICATION_JSON));


        System.out.println("Temps changé");

    }

    public static Response POST(String ipAddress, String service, Entity media){
        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target(ipAddress + service);
        Invocation.Builder b = resource.request();
        return b.post(media);
    }

}

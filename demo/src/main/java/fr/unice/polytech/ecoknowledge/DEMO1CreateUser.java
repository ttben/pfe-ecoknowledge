package fr.unice.polytech.ecoknowledge;

import com.google.gson.JsonObject;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Sébastien on 13/12/2015.
 */

public class DEMO1CreateUser {

    public static void main(String[] args) {

        JsonObject userDescription = new JsonObject();
        userDescription.addProperty("id", "620643fd-c7a4-4d41-aed9-bfe0f7a914b1");
        userDescription.addProperty("profilePic", "http://media.steampowered.com/steamcommunity/public/images/avatars/b3/b3a88d804414663378bfd9c2b9a4f1d6834831e0_full.jpg");
        userDescription.addProperty("firstName", "Hugo");
        userDescription.addProperty("lastName", "Simond");

        JsonObject mapping = new JsonObject();
        mapping.addProperty("TMP", "TEMP_443V");
        mapping.addProperty("TMP2", "TEMP_555");
        userDescription.add("symbolicNameToSensorNameMap", mapping);

        Response r = POST("http://localhost:8080/Ecoknowledge/", "users",
                Entity.entity(userDescription.toString(), MediaType.APPLICATION_JSON));

        System.out.println(r.getStatus()==200?"OK":"Exemple echoué");

    }

    public static Response POST(String ipAddress, String service, Entity media){
        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target(ipAddress + service);
        Invocation.Builder b = resource.request();
        return b.post(media);
    }
}

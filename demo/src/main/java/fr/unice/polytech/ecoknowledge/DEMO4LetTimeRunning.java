package fr.unice.polytech.ecoknowledge;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Sébastien on 13/12/2015.
 */
public class DEMO4LetTimeRunning {


    public static void main(String[] args){
/*
        JsonObject date = new JsonObject();
        date.addProperty("date", "2015-12-18T12:00:00Z");

        POST("http://localhost:8080/Ecoknowledge/", "test/clock",
                Entity.entity(date.toString(), MediaType.APPLICATION_JSON));

        JsonObject fakeData1 = new JsonObject();
        fakeData1.addProperty("sensor","TEMP_443V");
        fakeData1.addProperty("data", 23);
        fakeData1.addProperty("date", "2015-12-14T18:10:10Z");

        POST("http://localhost:8080/Ecoknowledge/", "test/stub",
                Entity.entity(fakeData1.toString(), MediaType.APPLICATION_JSON));


        JsonObject fakeData2 = new JsonObject();
        fakeData2.addProperty("sensor","TEMP_443V");
        fakeData2.addProperty("data", 21);
        fakeData2.addProperty("date", "2015-12-15T18:10:10Z");

        POST("http://localhost:8080/Ecoknowledge/", "test/stub",
                Entity.entity(fakeData2.toString(), MediaType.APPLICATION_JSON));*/

        String output = GET("http://localhost:8080/Ecoknowledge/", "challenges").readEntity(String.class);
        String challenge = output.split("\"")[3];

        JsonObject take = new JsonObject();
        take.addProperty("user", "620643fd-c7a4-4d41-aed9-bfe0f7a914b1");
        take.addProperty("challenge", challenge);

        Response r = POST("http://localhost:8080/Ecoknowledge/", "goals",
                Entity.entity(take.toString(), MediaType.APPLICATION_JSON));

        System.out.println(r.getStatus()==200?"OK":"Exemple echoué");

    }

    public static Response POST(String ipAddress, String service, Entity media){
        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target(ipAddress + service);
        Invocation.Builder b = resource.request();
        return b.post(media);
    }

    public static Response GET(String ipAddress, String service){
        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target(ipAddress + service);
        Invocation.Builder b = resource.request();
        return b.get();
    }
}

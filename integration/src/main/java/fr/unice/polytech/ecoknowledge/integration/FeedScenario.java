package fr.unice.polytech.ecoknowledge.integration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.joda.time.DateTime;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Sebastien on 01/03/2016.
 */
public class FeedScenario {

    private final static int ITERATIONS = 20;
    private final static int TIME_BETWEEN_ITERATIONS = 1000;
    private static final String URL_OF_ECOKNOWLEDGE_BACKEND_SERVER = "http://localhost:8080/fakeDataSource/";
    private static final String SERVICE = "fakeData/";
    private HashMap<String, JsonArray> values;


    public FeedScenario() throws InterruptedException {

        createValues();
        for(int i = 0; i < ITERATIONS; i++){
            for(String sensor : values.keySet()){

                JsonObject value = (JsonObject) values.get(sensor).get(i);

                sendFakeData(sensor, new DateTime(value.get("date").getAsLong()), value.get("value").getAsDouble());
            }
            Thread.currentThread().sleep(TIME_BETWEEN_ITERATIONS);
        }


    }

    private void createValues() {

        values = new HashMap<>();

        // TMP_CLIM
        JsonArray tmpValues = generateSpecificRandomValuesBetweenDate(ITERATIONS, 16, 26,
                new DateTime(2016, 1, 1, 0, 0, 0).getMillis(),
                new DateTime(2016, 3, 30, 0, 0, 0).getMillis());

        tmpValues.addAll(generateSpecificRandomValuesBetweenDate(ITERATIONS, 13, 22,
                new DateTime(2015, 12, 1, 0, 0, 0).getMillis(),
                new DateTime(2015, 12, 31, 0, 0, 0).getMillis()));
        values.put("TMP_CLIM", tmpValues);

        // TMP_AMB
        JsonArray tmpAValues = generateSpecificRandomValuesBetweenDate(ITERATIONS, 15, 25,
                new DateTime(2016, 1, 1, 0, 0, 0).getMillis(),
                new DateTime(2016, 3, 30, 0, 0, 0).getMillis());

        tmpAValues.addAll(generateSpecificRandomValuesBetweenDate(ITERATIONS, 12, 20,
                new DateTime(2015, 12, 1, 0, 0, 0).getMillis(),
                new DateTime(2015, 12, 31, 0, 0, 0).getMillis()));
        values.put("TMP_AMB", tmpAValues);

        // DOOR_OPEN
        values.put("DOOR_OPEN", generateSpecificRandomBitBetweenDate(ITERATIONS, 0.2,
                new DateTime(2016, 1, 1, 0, 0, 0).getMillis(),
                new DateTime(2016, 3, 30, 0, 0, 0).getMillis()));

        // WINDOW_OPEN
        values.put("WINDOW_OPEN", generateSpecificRandomBitBetweenDate(ITERATIONS, 0.3,
                new DateTime(2016, 1, 1, 0, 0, 0).getMillis(),
                new DateTime(2016, 3, 30, 0, 0, 0).getMillis()));

        // SOUND
        values.put("SOUND", generateSpecificRandomValuesBetweenDate(ITERATIONS, 15, 80,
                new DateTime(2016, 1, 1, 0, 0, 0).getMillis(),
                new DateTime(2016, 3, 30, 0, 0, 0).getMillis()));
    }

    private static JsonArray generateRandomValuesBetweenDateWithSpecificJump(long jump, int lowerLimit, int upperLimit, long dateStart, long dateEnd) {
        JsonArray values = new JsonArray();

        long currentTime = dateStart;

        for (long time = currentTime; time <= dateEnd; time = time + (jump)) {
            double randomValue = ThreadLocalRandom.current().nextDouble(lowerLimit, upperLimit);

            JsonObject jsonValue = new JsonObject();
            jsonValue.addProperty("date", time);
            jsonValue.addProperty("value", randomValue);

            values.add(jsonValue);
        }

        return values;
    }

    private static JsonArray generateRandomValuesBetweenDateWithSpecificJump(long jump, double probability, long dateStart, long dateEnd) {
        JsonArray values = new JsonArray();

        long currentTime = dateStart;

        for (long time = currentTime; time <= dateEnd; time = time + (jump)) {

            Boolean randomValue = ThreadLocalRandom.current().nextDouble(0., 1.) < probability;

            JsonObject jsonValue = new JsonObject();
            jsonValue.addProperty("date", time);
            jsonValue.addProperty("value", randomValue?1:0);

            values.add(jsonValue);
        }

        return values;
    }

    public static JsonArray generateSpecificRandomValuesBetweenDate(int nbOfValueToGenerate, int lowerLimit, int upperLimit, long dateStart, long dateEnd) {
        if (dateStart > dateEnd) {
            throw new IllegalArgumentException("start is after end");
        }

        long jump = (dateEnd - dateStart) / nbOfValueToGenerate;

        return generateRandomValuesBetweenDateWithSpecificJump(jump, lowerLimit, upperLimit, dateStart, dateEnd);
    }

    public static JsonArray generateSpecificRandomBitBetweenDate(int nbOfValueToGenerate, double probability, long dateStart, long dateEnd){
        if (dateStart > dateEnd) {
            throw new IllegalArgumentException("start is after end");
        }

        long jump = (dateEnd - dateStart) / nbOfValueToGenerate;

        return generateRandomValuesBetweenDateWithSpecificJump(jump, probability, dateStart, dateEnd);
    }

    private static String postRequest(String url, String service, JsonObject payload) throws InterruptedException {
        Response statusPostResponse = POST(url, service, payload);

        Object entity = statusPostResponse.readEntity(String.class);
        System.out.println("Receiving response : " + statusPostResponse + " containing : " + entity);

        return entity.toString();
    }

    public static Response POST(String ipAddress, String service, JsonObject media) {
        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target(ipAddress + service);
        Invocation.Builder b = resource.request();
        System.out.println("\t---> Sending request " + media.toString() + " to " + ipAddress + service + "'");

        Entity e = Entity.entity(media.toString(), MediaType.APPLICATION_JSON);

        return b.post(e);
    }

    private String sendFakeData(String sensorName, DateTime date, double value) throws InterruptedException {
        String aSensorName = sensorName;

        JsonObject firstPayloadValue = new JsonObject();
        firstPayloadValue.addProperty("date", date.getMillis());
        firstPayloadValue.addProperty("value", value);

        JsonObject payload = new JsonObject();
        payload.addProperty("targetSensor", sensorName);
        payload.add("value", firstPayloadValue);

        return postRequest(URL_OF_ECOKNOWLEDGE_BACKEND_SERVER, SERVICE, payload);
    }

    public static void main(String[] args) {
        try {
            new FeedScenario();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

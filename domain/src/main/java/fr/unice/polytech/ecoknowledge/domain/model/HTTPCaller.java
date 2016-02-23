package fr.unice.polytech.ecoknowledge.domain.model;

import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Benjamin on 22/02/2016.
 */
public class HTTPCaller {

	public String sendGet(SensorNeeds sensorNeeds) throws Exception {

		String line;
		StringBuffer jsonString = new StringBuffer();
		try {

			URL url = new URL("http://localhost:8081/webTiersFeeder/");

			JsonObject payloadJsonObject = new JsonObject();
			payloadJsonObject.addProperty("targetSensor", sensorNeeds.getTargetSensor());
			payloadJsonObject.addProperty("dateStart", sensorNeeds.getDateStart());
			payloadJsonObject.addProperty("dateEnd", sensorNeeds.getDateEnd());
			payloadJsonObject.addProperty("frequency", 250);

			//escape the double quotes in json string
			String payload= payloadJsonObject.toString();

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			writer.write(payload);
			writer.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = br.readLine()) != null) {
				jsonString.append(line);
			}
			br.close();
			connection.disconnect();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

		return jsonString.toString();

	}

}

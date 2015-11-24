package fr.unice.polytech.ecoknowledge.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Benjamin on 23/11/2015.
 */
public class ChallengeTest {

	private Challenge challenge;

	@Before
	public void setUp() {

	}

	@Test
	public void basicTest() {
		JsonObject challengeJsonObject = new JsonObject();
		challengeJsonObject.addProperty("recurrence", "oui récurent ma gueuele");
		challengeJsonObject.addProperty("type", "overall");

		JsonObject lifeSpanJsonObject = new JsonObject();
		lifeSpanJsonObject.addProperty("start", "2001-07-04T12:08:56.235-0700");
		lifeSpanJsonObject.addProperty("start", "2001-11-04T02:08:56.235-0700");
		challengeJsonObject.add("lifeSpan", lifeSpanJsonObject);

		JsonObject badgeJsonObject = new JsonObject();
		badgeJsonObject.addProperty("name", "Yeti master !");
		badgeJsonObject.addProperty("image", "http://www.google.com");
		badgeJsonObject.addProperty("reward",69);
		challengeJsonObject.add("badge", badgeJsonObject);


		ObjectMapper m = new ObjectMapper();
		Object result = null;

		try {
			result = m.readValue(challengeJsonObject.toString(), Challenge.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertNotNull(result);

	}

}

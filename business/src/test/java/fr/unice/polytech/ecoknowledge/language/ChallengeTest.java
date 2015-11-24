package fr.unice.polytech.ecoknowledge.language;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ChallengeTest {

	private Challenge challenge;
	JsonObject challengeJsonObject = new JsonObject();
	JsonObject lifeSpanJsonObject = new JsonObject();
	JsonObject badgeJsonObject = new JsonObject();

	@Before
	public void setUp() {
		challengeJsonObject.addProperty("recurrence", "oui récurent ma gueuele");
		challengeJsonObject.addProperty("type", "overall");

		lifeSpanJsonObject.addProperty("start", "2001-07-04T12:08:56.235-0700");
		lifeSpanJsonObject.addProperty("end", "2001-11-04T02:08:56.235-0700");
		challengeJsonObject.add("lifeSpan", lifeSpanJsonObject);

		badgeJsonObject.addProperty("name", "Yeti master !");
		badgeJsonObject.addProperty("image", "http://www.google.com");
		badgeJsonObject.addProperty("reward",69);
		challengeJsonObject.add("badge", badgeJsonObject);
	}

	@Test
	public void basicTest() throws IOException {
		ObjectMapper m = new ObjectMapper();
		challenge = m.readValue(challengeJsonObject.toString(), Challenge.class);
		System.out.println("LOL ::: " + challenge.getTimeSpan().getEnd());
	}
}

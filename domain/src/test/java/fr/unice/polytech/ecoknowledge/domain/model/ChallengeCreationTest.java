package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.StandardCondition;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ChallengeCreationTest {
	public static final String FILENAME = "challenge-example-sample2.json";

	JsonObject jsonObject2 = null;

	@Before
	public void loadJsonFile() throws Exception {
		jsonObject2 = TestUtils.getFakeJson(FILENAME);
	}

	@Test
	public void aChallenge_CanHaveTargetTime() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Challenge challenge = (Challenge) objectMapper.readValue(jsonObject2.toString(), Challenge.class);

		StandardCondition sc = (StandardCondition) challenge.getLevels().get(0).getConditions().get(0);

		assertEquals(Arrays.asList(new AbstractMap.SimpleEntry<Integer, Integer>(8, 11)), sc.getTargetDays().getDayMoment());
		assertEquals(new AbstractMap.SimpleEntry<Integer, Integer>(1, 5), sc.getTargetDays().getWeekMoment());
	}

}

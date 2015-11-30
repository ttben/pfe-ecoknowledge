package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.data.ChallengePersistence;
import fr.unice.polytech.ecoknowledge.domain.TestUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ChallengePersistenceTest {

	JsonObject jsonObject = null;

	static Challenge aChallenge = null;

	@Before
	public void loadJsonFile() {

		jsonObject = TestUtils.getFakeJson();
	}


	@Test
	public void aChallenge_WhenCreatedAndPersisted_CanBeCreatedAndIsTheSame() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Challenge challenge = (Challenge) objectMapper.readValue(jsonObject.toString(), Challenge.class);

		jsonObject.addProperty("id",""+challenge.getId());
		ChallengePersistence.store(jsonObject);

		JsonObject result = ChallengePersistence.read(challenge.getId().toString());
		aChallenge = (Challenge)objectMapper.readValue(result.toString(), Challenge.class);

		assertEquals(challenge, aChallenge);
	}

	@AfterClass
	public static void tearDown() {
		ChallengePersistence.drop(aChallenge.getId().toString());
	}
}

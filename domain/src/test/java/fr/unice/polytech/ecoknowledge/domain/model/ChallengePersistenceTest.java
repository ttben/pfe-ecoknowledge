package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.data.DataPersistence;
import fr.unice.polytech.ecoknowledge.domain.TestUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

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
		DataPersistence.store(DataPersistence.Collections.CHALLENGE, jsonObject);

		JsonObject result = DataPersistence.read(DataPersistence.Collections.CHALLENGE,challenge.getId().toString());
		aChallenge = (Challenge)objectMapper.readValue(result.toString(), Challenge.class);

		assertEquals(challenge, aChallenge);
	}

	@AfterClass
	public static void tearDown() {
		DataPersistence.drop(DataPersistence.Collections.CHALLENGE,aChallenge.getId().toString());
	}
}

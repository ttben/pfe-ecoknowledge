package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.data.DataPersistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserPersistenceTest {

	private User user;
	private JsonObject jsonUserDescription;

	@Before
	public void setUp() {
		this.user = getExpectedUser();
		this.jsonUserDescription = getUserDescription();
	}

	@AfterClass
	public static void eraseUsers(){
		DataPersistence.drop(DataPersistence.Collections.USER);
	}

	@Test
	public void aUser_WhenBuiltWithNameAndPersonalMapping_ShouldHaveIt() throws IOException {
		JsonObject jsonObject = this.jsonUserDescription;

		ObjectMapper objectMapper = new ObjectMapper();
		User user = (User) objectMapper.readValue(jsonObject.toString(), User.class);

		assertEquals(getExpectedUser(), user);
	}

	@Test
	public void aUser_WhenBuiltWithoutID_ShouldHaveOne() throws IOException {
		assertNotNull(user.getId());
	}

	@Test
	public void aUser_WhenPersist_ShouldNotThrow() throws IOException {
		JsonObject jsonObject = this.jsonUserDescription;
		jsonObject.addProperty("id", user.getId().toString());

		DataPersistence.store(DataPersistence.Collections.USER, jsonObject);
	}

	@Test
	public void aUser_WhenPersist_ShouldBeRebuilt() throws IOException {
		JsonObject jsonObject = this.jsonUserDescription;
		jsonObject.addProperty("id", user.getId().toString());

		DataPersistence.store(DataPersistence.Collections.USER, jsonObject);
		JsonObject result = DataPersistence.read(DataPersistence.Collections.USER, user.getId().toString());
		ObjectMapper objectMapper = new ObjectMapper();
		User anotherUser = (User) objectMapper.readValue(result.toString(), User.class);

		assertEquals(getExpectedUser(), anotherUser);
	}



	private JsonObject getUserDescription() {
		JsonObject jsonObject = new JsonObject();

		JsonObject personalMapping = new JsonObject();
		personalMapping.addProperty("TMP_CLI", "TEMP_433V");

		jsonObject.addProperty("firstName", "johnny");
		jsonObject.addProperty("lastName", "depp");
		jsonObject.add("personalMapping", personalMapping);

		return jsonObject;
	}

	private User getExpectedUser() {
		Map<String, String> expectedMap = new HashMap<>();
		expectedMap.put("TMP_CLI", "TEMP_433V");
		User expectedUser = new User(null, "johnny", "depp", null, null, null, expectedMap);
		return expectedUser;
	}
}

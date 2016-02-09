package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import core.MongoDBConnector;
import exceptions.NotReadableElementException;
import exceptions.NotSavableElementException;
import exceptions.UserNotFoundException;
import fr.unice.polytech.ecoknowledge.domain.data.MongoDBHandler;
import org.junit.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserPersistenceTest {

	static private User user;
	private JsonObject jsonUserDescription;

	static String oldDBName;
	static String testDBName = "challengePersistenceTest";

	@Before
	@Ignore
	public void setUp() {
		oldDBName = MongoDBConnector.DB_NAME;
		MongoDBConnector.DB_NAME = testDBName;
		this.user = getExpectedUser();
		this.jsonUserDescription = getUserDescription();
	}

	@AfterClass
	@Ignore
	public static void eraseUsers() {
		MongoDBConnector.DB_NAME = oldDBName;
		// TODO: 06/12/2015 DataPersistence.drop(DataPersistence.Collections.USER, user.getId().toString());
	}

	@Test
	@Ignore
	public void aUser_WhenBuiltWithNameAndPersonalMapping_ShouldHaveIt() throws IOException {
		JsonObject jsonObject = this.jsonUserDescription;

		ObjectMapper objectMapper = new ObjectMapper();
		User user = (User) objectMapper.readValue(jsonObject.toString(), User.class);

		assertEquals(UserPersistenceTest.user, user);
	}

	@Test
	@Ignore
	public void aUser_WhenBuiltWithoutID_ShouldHaveOne() throws IOException {
		assertNotNull(user.getId());
	}

	@Test
	@Ignore
	public void aUser_WhenPersist_ShouldNotThrow() throws IOException, NotSavableElementException {
		MongoDBHandler.getInstance().store(user);
	}

	@Test
	@Ignore
	public void aUser_WhenPersist_ShouldBeRebuilt() throws IOException, NotSavableElementException, UserNotFoundException, NotReadableElementException {
		MongoDBHandler.getInstance().store(user);
		User anotherUser = MongoDBHandler.getInstance().readUserByID(user.getId().toString());

		// TODO: 06/12/2015  DataPersistence.drop(DataPersistence.Collections.USER, jsonObject.get("id").getAsString());

		assertEquals(UserPersistenceTest.user, anotherUser);
	}


	private JsonObject getUserDescription() {
		JsonObject jsonObject = new JsonObject();

		JsonObject personalMapping = new JsonObject();
		personalMapping.addProperty("TMP_CLI", "TEMP_433V");

		jsonObject.addProperty("firstName", "johnny");
		jsonObject.addProperty("lastName", "depp");
		jsonObject.add("symbolicNameToSensorNameMap", personalMapping);

		return jsonObject;
	}

	private User getExpectedUser() {
		Map<String, String> expectedMap = new HashMap<>();
		expectedMap.put("TMP_CLI", "TEMP_433V");
		User expectedUser = new User(null, "johnny", "depp", null, null, null, expectedMap);
		return expectedUser;
	}
}

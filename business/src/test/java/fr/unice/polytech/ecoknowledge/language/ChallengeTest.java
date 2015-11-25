package fr.unice.polytech.ecoknowledge.language;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.business.Challenge;
import org.codehaus.jackson.map.SerializationConfig;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ChallengeTest {

	private Challenge challenge;
	JsonObject challengeJsonObject = new JsonObject();
	JsonObject lifeSpanJsonObject = new JsonObject();
	JsonObject badgeJsonObject = new JsonObject();

	// FIXME: 25/11/2015 
	/*
	@Before
	public void setUp() {
		challengeJsonObject.addProperty("recurrence", "oui récurent ma gueuele");
		challengeJsonObject.addProperty("type", "standard");

		lifeSpanJsonObject.addProperty("start", "2001-07-04T12:08:56.235-0700");
		lifeSpanJsonObject.addProperty("end", "2001-11-04T02:08:56.235-0700");
		challengeJsonObject.add("lifeSpan", lifeSpanJsonObject);

		badgeJsonObject.addProperty("name", "Yeti master !");
		badgeJsonObject.addProperty("image", "http://www.google.com");
		badgeJsonObject.addProperty("reward",69);
		challengeJsonObject.add("badge", badgeJsonObject);

		JsonArray conditions = new JsonArray();
		challengeJsonObject.add("conditions", conditions);
	}

	@Test
	public void basicTest() throws IOException {
		ObjectMapper m = new ObjectMapper();
		challenge = m.readValue(challengeJsonObject.toString(), Challenge.class);
	}

	@Test
	public void test() throws IOException {
		org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
		//There are other configuration options you can set.  This is the one I needed.
		mapper.configure(SerializationConfig.Feature.AUTO_DETECT_GETTERS, true);

		org.codehaus.jackson.schema.JsonSchema schema = mapper.generateJsonSchema(Challenge.class);
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema));
	}

	@Test
	public void test2() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
		mapper.acceptJsonFormatVisitor(Challenge.class, visitor);
		com.fasterxml.jackson.module.jsonSchema.JsonSchema schema = visitor.finalSchema();
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema));

	}
	*/
}

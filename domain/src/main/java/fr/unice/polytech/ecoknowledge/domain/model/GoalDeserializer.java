package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.unice.polytech.ecoknowledge.data.ChallengePersistence;

import java.io.IOException;

public class GoalDeserializer extends JsonDeserializer<Goal> {
	@Override
	public Goal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		JsonNode node = jsonParser.getCodec().readTree(jsonParser);
		String userID = (node.get("user")).asText();
		String challengeID = (node.get("challenge")).asText();

		// FIXME: 01/12/2015 // TODO: 01/12/2015
		User user = new User();
		
		ChallengePersistence.read(challengeID);

		return null;
	}
}

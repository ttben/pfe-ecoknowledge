package fr.unice.polytech.ecoknowledge.domain.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.data.DataPersistence;
import fr.unice.polytech.ecoknowledge.domain.model.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.User;

import java.io.IOException;

public class GoalDeserializer extends JsonDeserializer<Goal> {
	@Override
	public Goal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		JsonNode node = jsonParser.getCodec().readTree(jsonParser);
		String userID = (node.get("user")).asText();
		String challengeID = (node.get("challenge")).asText();
		String goalID = null;

		if(node.get("id") != null) {
			goalID = node.get("id").asText();
		}

		JsonObject challengeJsonDescription = DataPersistence.read(DataPersistence.Collections.CHALLENGE,challengeID);
		JsonObject userJsonDescription = DataPersistence.read(DataPersistence.Collections.USER,userID);

		ObjectMapper objectMapper = new ObjectMapper();

		User user = (User) objectMapper.readValue(userJsonDescription.toString(), User.class);
		Challenge challenge = (Challenge) objectMapper.readValue(challengeJsonDescription.toString(), Challenge.class);

		Goal goal = new Goal(goalID, challenge, null, user);
		return goal;
	}
}
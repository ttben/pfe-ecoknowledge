package fr.unice.polytech.ecoknowledge.domain.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.ecoknowledge.data.exceptions.ChallengeNotFoundException;
import fr.unice.polytech.ecoknowledge.data.exceptions.NotReadableElementException;
import fr.unice.polytech.ecoknowledge.data.exceptions.UserNotFoundException;
import fr.unice.polytech.ecoknowledge.domain.data.MongoDBHandler;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.User;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.time.TimeBox;

import java.io.IOException;

public class GoalDeserializer extends JsonDeserializer<Goal> {
	@Override
	public Goal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		JsonNode node = jsonParser.getCodec().readTree(jsonParser);
		String userID = (node.get("user")).asText();
		String challengeID = (node.get("challenge")).asText();

		TimeBox timeBox = null;
		ObjectMapper mapper = new ObjectMapper();


		if (node.get("timeSpan") != null) {
			timeBox = mapper.readValue(node.get("timeSpan").asText(), TimeBox.class);
		}

		String goalID = null;

		if (node.get("id") != null) {
			goalID = node.get("id").asText();
		}

		String goalResultID = "";
		if (node.get("resultID") != null) {
			goalResultID = node.get("resultID").asText();
		}

		try {
			User user = MongoDBHandler.getInstance().readUserByID(userID);
			Challenge challenge = MongoDBHandler.getInstance().readChallengeByID(challengeID);
			Goal goal = new Goal(goalID, challenge, timeBox, user, goalResultID);
			return goal;

		} catch (UserNotFoundException | NotReadableElementException | ChallengeNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Can not deserialize goal", e);
		}
	}
}

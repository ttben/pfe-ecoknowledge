package fr.unice.polytech.ecoknowledge.domain.model.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.data.DataPersistence;
import fr.unice.polytech.ecoknowledge.domain.model.Badge;
import fr.unice.polytech.ecoknowledge.domain.model.Level;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.Condition;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Benjamin on 01/12/2015.
 */
public class LevelDeserializer extends JsonDeserializer<Level> {
	@Override
	public Level deserialize(com.fasterxml.jackson.core.JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		JsonNode node = jsonParser.getCodec().readTree(jsonParser);

		//	Retrieve name
		String name = (node.get("name")).asText();

		//	Retrieve conditions
		ObjectMapper objectMapper = new ObjectMapper();
		
		Condition[] conditions = objectMapper.readValue(node.get("conditions").toString(), Condition[].class);
		List<Condition> conditionList = Arrays.asList(conditions);

		//	Retrieve badge
		JsonNode badgeNode = node.get("badge");
		JsonObject badgeDescription = new JsonParser().parse(badgeNode.toString()).getAsJsonObject();

		//	If it is an ID, i.e. if we read a JsonObject from the DB (and not from client)
		if(badgeNode.isTextual()) {
			badgeDescription = DataPersistence.read(DataPersistence.BADGE_COLLECTION, badgeNode.asText());
		}

		Badge badge = objectMapper.readValue(badgeDescription.toString(), Badge.class);

		Level level = new Level(conditionList, name, badge);

		return level;
	}
}

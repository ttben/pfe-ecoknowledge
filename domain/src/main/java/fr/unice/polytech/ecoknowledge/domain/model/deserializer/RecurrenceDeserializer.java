package fr.unice.polytech.ecoknowledge.domain.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import fr.unice.polytech.ecoknowledge.domain.model.time.Recurrence;
import fr.unice.polytech.ecoknowledge.domain.model.time.RecurrenceType;

import java.io.IOException;

public class RecurrenceDeserializer extends JsonDeserializer<Recurrence> {
	@Override
	public Recurrence deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		JsonNode node = jsonParser.getCodec().readTree(jsonParser);
		String type = (node.get("type")).asText();
		Integer unit = (node.get("unit")).asInt();

		return new Recurrence(RecurrenceType.fromString(type), unit);
	}
}

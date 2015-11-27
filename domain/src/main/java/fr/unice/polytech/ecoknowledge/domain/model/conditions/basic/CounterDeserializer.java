package fr.unice.polytech.ecoknowledge.domain.model.conditions.basic;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class CounterDeserializer extends JsonDeserializer<Counter> {

	@Override
	public Counter deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
		String type = (node.get("type")).asText();
		Double threshold = (node.get("threshold")).asDouble();

		return new Counter(threshold, CounterType.fromString(type));
	}
}

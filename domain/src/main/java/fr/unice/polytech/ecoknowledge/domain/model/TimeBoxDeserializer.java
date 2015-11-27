package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.joda.time.DateTime;

import java.io.IOException;

/**
 * Created by Benjamin on 26/11/2015.
 */
public class TimeBoxDeserializer extends JsonDeserializer<TimeBox> {
	@Override
	public TimeBox deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
		String start =(node.get("start")).asText();
		String end =(node.get("end")).asText();
/*
		String itemName = node.get("itemName").asText();
		int userId = (Integer) ((IntNode) node.get("createdBy")).numberValue();
*/
		DateTime startDate = DateTime.parse(start);
		DateTime endDate = DateTime.parse(end);

		return new TimeBox(startDate, endDate);
	}
}

package fr.unice.polytech.ecoknowledge.domain.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.model.TimeBox;
import org.joda.time.DateTime;

import java.io.IOException;

/**
 * Created by Benjamin on 26/11/2015.
 */
public class TimeBoxSerializer extends JsonSerializer<TimeBox> {

	@Override
	public void serialize(TimeBox timeBox, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

		String start = timeBox.getStart().toString();
		String end = timeBox.getEnd().toString();

		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("start", start);
		jsonGenerator.writeStringField("end", end);
		jsonGenerator.writeEndObject();
	}
}

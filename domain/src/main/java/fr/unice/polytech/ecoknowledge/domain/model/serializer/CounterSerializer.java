package fr.unice.polytech.ecoknowledge.domain.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.Counter;

import java.io.IOException;

public class CounterSerializer extends JsonSerializer<Counter> {
	@Override
	public void serialize(Counter value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		jgen.writeStartObject();

		jgen.writeNumberField("threshold", value.getThreshold());
		jgen.writeStringField("type", value.getType().getCounterType());

		jgen.writeEndObject();
	}
}
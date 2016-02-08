package fr.unice.polytech.ecoknowledge.domain.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.time.TimeFilter;
import fr.unice.polytech.ecoknowledge.domain.model.time.Recurrence;

import java.io.IOException;

/**
 * Created by Benjamin on 06/12/2015.
 */
public class RecurrenceSerializer  extends JsonSerializer<Recurrence> {

	@Override
	public void serialize(Recurrence timeFilter, JsonGenerator jsonGenerator,
						  SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

		String type =timeFilter.getRecurrenceType().getRecurrenceType();
		String unit = timeFilter.getUnit().toString();

		jsonGenerator.writeStartObject();

		jsonGenerator.writeStringField("type", type);
		jsonGenerator.writeStringField("unit", unit);

		jsonGenerator.writeEndObject();
	}
}
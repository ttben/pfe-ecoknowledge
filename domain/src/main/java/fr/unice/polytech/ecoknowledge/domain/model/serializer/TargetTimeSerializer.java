package fr.unice.polytech.ecoknowledge.domain.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.time.TimeFilter;

import java.io.IOException;

/**
 * Created by Benjamin on 06/12/2015.
 */
public class TargetTimeSerializer extends JsonSerializer<TimeFilter> {

	@Override
	public void serialize(TimeFilter timeFilter, JsonGenerator jsonGenerator,
						  SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

		String hours = timeFilter.getHoursStr();
		String days = timeFilter.getDaysStr();

		jsonGenerator.writeStartObject();

		jsonGenerator.writeStringField("hours", hours);
		jsonGenerator.writeStringField("days", days);

		jsonGenerator.writeEndObject();
	}
}

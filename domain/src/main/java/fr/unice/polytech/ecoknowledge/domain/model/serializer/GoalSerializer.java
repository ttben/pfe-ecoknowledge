package fr.unice.polytech.ecoknowledge.domain.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;

import java.io.IOException;

/**
 * Created by Benjamin on 03/12/2015.
 */
public class GoalSerializer extends JsonSerializer<Goal> {
	@Override
	public void serialize(Goal value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		jgen.writeStartObject();

		jgen.writeStringField("challenge", value.getChallengeDefinition().getId().toString());
		jgen.writeStringField("user", value.getUser().getId().toString());
		jgen.writeStringField("id", value.getId().toString());

		if (value.getGoalResultID() != null) {
			jgen.writeStringField("resultID", value.getGoalResultID().toString());
		}

		if (value.getTimeSpan() != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			String timeSpanDescription = objectMapper.writeValueAsString(value.getTimeSpan());
			jgen.writeStringField("timeSpan", timeSpanDescription);
		}

		jgen.writeEndObject();
	}
}

package fr.unice.polytech.ecoknowledge.domain.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.time.DayMoment;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.time.TimeFilter;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.time.WeekMoment;

import java.io.IOException;

/**
 * Created by Sébastien on 02/12/2015.
 */
public class TargetTimeDeserializer extends JsonDeserializer<TimeFilter> {
    @Override
    public TimeFilter deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String hours = (node.get("hours")).asText();
        String days = (node.get("days")).asText();

        return new TimeFilter(DayMoment.fromString(hours).getHours(), WeekMoment.fromString(days).getDays());
    }
}

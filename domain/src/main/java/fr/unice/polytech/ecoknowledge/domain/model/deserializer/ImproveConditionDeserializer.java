package fr.unice.polytech.ecoknowledge.domain.model.deserializer;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.Condition;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.improve.ImproveCondition;
import fr.unice.polytech.ecoknowledge.domain.model.time.Clock;
import fr.unice.polytech.ecoknowledge.domain.model.time.TimeBox;
import fr.unice.polytech.ecoknowledge.domain.model.time.TimeSpanGenerator;

import java.io.IOException;

/**
 * Created by Sébastien on 06/12/2015.
 */
public class ImproveConditionDeserializer extends JsonDeserializer<Condition> {
    @Override
    public Condition deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String referencePeriodString = null;
        try {
            referencePeriodString = (node.get("referencePeriod").asText());
        } catch (Throwable t){}

        TimeBox tb;
        if(referencePeriodString != null
            && (referencePeriodString.equals("week") || referencePeriodString.equals("month"))) {
            if (referencePeriodString.equals("week")) {
                // FIXME: 06/12/2015
                tb = TimeSpanGenerator.generateLastWeek(new Clock());
            } else {
                // FIXME: 06/12/2015
                tb = TimeSpanGenerator.generateLastMonth(new Clock());
            }
        }
        else {
            ObjectMapper mapper = new ObjectMapper();
            tb = mapper.readValue(node.get("referencePeriod").toString(), TimeBox.class);
        }

        Double threshold = (node.get("threshold")).asDouble();
        String improvementType = (node.get("improvementType")).asText();
        String symbolicName = (node.get("symbolicName")).asText();

        return new ImproveCondition(tb, threshold, improvementType, symbolicName);

    }
}

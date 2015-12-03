package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.unice.polytech.ecoknowledge.domain.model.deserializer.TimeBoxDeserializer;
import fr.unice.polytech.ecoknowledge.domain.model.serializer.TimeBoxSerializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

@JsonSerialize(using = TimeBoxSerializer.class)
@JsonDeserialize(using = TimeBoxDeserializer.class)
public class TimeBox {


	private DateTime start;
	private DateTime end;

	public TimeBox(@JsonProperty(value = "start", required = true) DateTime start,
				   @JsonProperty(value = "end", required = true) DateTime end) {
		this.start = start;
		this.end = end;
	}

	public DateTime getStart() {
		return start;
	}

	public void setStart(DateTime start) {
		this.start = start;
	}

	public DateTime getEnd() {
		return end;
	}

	public void setEnd(DateTime end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return start.toString(DateTimeFormat.forPattern("dd/mm/yyyy HH:mm:ss"))
				+ "  -  "
				+ end.toString(DateTimeFormat.forPattern("dd/mm/yyyy HH:mm:ss"));
	}

	@Override
	public boolean equals(Object o) {
		if(! (o instanceof TimeBox)) {
			return false;
		}

		TimeBox timeBox = (TimeBox)o;
		return timeBox.start.equals(start) && timeBox.end.equals(end);
	}
}
package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;


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
}
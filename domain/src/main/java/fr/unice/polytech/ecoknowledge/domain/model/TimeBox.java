package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class TimeBox {


	private Date start;
	private Date end;

	public TimeBox(@JsonProperty(value = "start", required = true) Date start,
				   @JsonProperty(value = "end", required = true) Date end) {
		this.start = start;
		this.end = end;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
}
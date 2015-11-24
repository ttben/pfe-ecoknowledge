package business;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class TimeBox {

	private Date start;
	private Date end;

	@JsonCreator
	public TimeBox(@JsonProperty("start") Date start, @JsonProperty("end") Date end) {
		this.start = start;
		this.end = end;
	}
}
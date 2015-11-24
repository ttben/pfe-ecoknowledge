package business.overall.expression;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Comparator {

	private String comparator;

	@JsonCreator
	public Comparator(String comparator) {
		this.comparator = comparator;
	}
}
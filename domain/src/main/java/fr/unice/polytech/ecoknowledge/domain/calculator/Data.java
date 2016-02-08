package fr.unice.polytech.ecoknowledge.domain.calculator;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class Data {

	private Double value;
	private DateTime date;

	public Data(Double value, DateTime date) {
		this.value = value;
		this.date = date;
	}


	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return date.toString(DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss")) + " - value : " + value;
	}
}

package fr.unice.polytech.ecoknowledge.domain.calculator;

import java.util.Date;

public class Data {

	private Double value;
	private Date date;

	public Data(Double value, Date date) {
		this.value = value;
		this.date = date;
	}


	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}

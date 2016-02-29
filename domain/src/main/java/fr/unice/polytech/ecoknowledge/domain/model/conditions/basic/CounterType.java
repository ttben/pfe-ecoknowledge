package fr.unice.polytech.ecoknowledge.domain.model.conditions.basic;

public enum CounterType {

	TIMES("times"),
	PERCENT_OF_TIME("percent-of-time");

	private String counterType;

	CounterType(String counterType) {
		this.counterType = counterType;
	}

	public static CounterType fromString(String text) {
		if (text != null) {
			for (CounterType b : CounterType.values()) {
				if (text.equalsIgnoreCase(b.counterType)) {
					return b;
				}
			}
		}
		throw new IllegalArgumentException("Field " + text + " does not exist on counter object");
	}

	public String getCounterType() {
		return counterType;
	}

	public void setCounterType(String counterType) {
		this.counterType = counterType;
	}
}

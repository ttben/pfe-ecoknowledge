package fr.unice.polytech.ecoknowledge.domain.model.exceptions;

public class ChallengeNotFoundException extends RuntimeException {
	public ChallengeNotFoundException(String message) {
		super(message);
	}

	public ChallengeNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}

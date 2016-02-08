package fr.unice.polytech.ecoknowledge.domain.model.exceptions;

import fr.unice.polytech.ecoknowledge.domain.data.exceptions.EcoknowledgeDataException;

public class ChallengeNotFoundException extends EcoknowledgeDataException {
	public ChallengeNotFoundException(String message) {
		super(message);
	}

	public ChallengeNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}

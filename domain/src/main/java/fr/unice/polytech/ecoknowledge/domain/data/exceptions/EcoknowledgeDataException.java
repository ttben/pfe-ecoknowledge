package fr.unice.polytech.ecoknowledge.domain.data.exceptions;

import fr.unice.polytech.ecoknowledge.domain.exceptions.EcoKnowledgeException;

/**
 * Created by Benjamin on 06/12/2015.
 */
public class EcoknowledgeDataException extends EcoKnowledgeException {

	public EcoknowledgeDataException() {
	}

	public EcoknowledgeDataException(String message) {
		super(message);
	}

	public EcoknowledgeDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public EcoknowledgeDataException(Throwable cause) {
		super(cause);
	}

	public EcoknowledgeDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

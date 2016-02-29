package fr.unice.polytech.ecoknowledge.data.exceptions;

/**
 * Created by Benjamin on 06/12/2015.
 */
public class EcoKnowledgeException extends Exception {
	public EcoKnowledgeException() {
	}

	public EcoKnowledgeException(String message) {
		super(message);
	}

	public EcoKnowledgeException(String message, Throwable cause) {
		super(message, cause);
	}

	public EcoKnowledgeException(Throwable cause) {
		super(cause);
	}

	public EcoKnowledgeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

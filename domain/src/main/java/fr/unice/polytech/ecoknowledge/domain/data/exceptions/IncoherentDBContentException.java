package fr.unice.polytech.ecoknowledge.domain.data.exceptions;

public class IncoherentDBContentException extends EcoknowledgeDataException {
	public IncoherentDBContentException() {
	}

	public IncoherentDBContentException(String message) {
		super(message);
	}

	public IncoherentDBContentException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncoherentDBContentException(Throwable cause) {
		super(cause);
	}

	public IncoherentDBContentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

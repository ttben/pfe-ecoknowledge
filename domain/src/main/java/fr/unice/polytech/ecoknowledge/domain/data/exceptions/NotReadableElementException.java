package fr.unice.polytech.ecoknowledge.domain.data.exceptions;

/**
 * Created by Benjamin on 05/12/2015.
 */
public class NotReadableElementException  extends Exception {
	public NotReadableElementException() {
	}

	public NotReadableElementException(String message) {
		super(message);
	}

	public NotReadableElementException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotReadableElementException(Throwable cause) {
		super(cause);
	}

	public NotReadableElementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
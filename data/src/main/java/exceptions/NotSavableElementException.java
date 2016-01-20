package exceptions;

/**
 * Created by Benjamin on 05/12/2015.
 */
public class NotSavableElementException extends EcoknowledgeDataException {
	public NotSavableElementException() {}

	public NotSavableElementException(String message) {
		super(message);
	}

	public NotSavableElementException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotSavableElementException(Throwable cause) {
		super(cause);
	}

	public NotSavableElementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

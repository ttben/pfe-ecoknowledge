package exceptions;

public class ChallengeNotFoundException extends EcoknowledgeDataException {
	public ChallengeNotFoundException(String message) {
		super(message);
	}

	public ChallengeNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}

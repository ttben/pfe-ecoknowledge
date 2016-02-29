package fr.unice.polytech.ecoknowledge.data.exceptions;

public class UserNotFoundException extends Exception {
	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotFoundException(String message) {
		super(message);
	}
}

package fr.unice.polytech.ecoknowledge.data.exceptions;

/**
 * Created by Benjamin on 06/12/2015.
 */
public class GoalNotFoundException extends Exception {
	public GoalNotFoundException(String message) {
		super(message);
	}

	public GoalNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}

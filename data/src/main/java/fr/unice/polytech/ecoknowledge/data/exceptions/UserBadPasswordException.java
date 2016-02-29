package fr.unice.polytech.ecoknowledge.data.exceptions;

/**
 * Created by Sébastien on 25/02/2016.
 */
public class UserBadPasswordException extends Exception  {
    public UserBadPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserBadPasswordException(String message) {
        super(message);
    }
}

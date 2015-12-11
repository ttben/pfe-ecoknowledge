package fr.unice.polytech.ecoknowledge.domain.model.exceptions;

import fr.unice.polytech.ecoknowledge.domain.exceptions.EcoKnowledgeException;

/**
 * Created by Sébastien on 11/12/2015.
 */
public class InvalidGoalTimespanOverChallengeException extends EcoKnowledgeException {
    public InvalidGoalTimespanOverChallengeException(String s) {
        super(s);
    }
}

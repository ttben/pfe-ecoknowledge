package fr.unice.polytech.ecoknowledge.language;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class Challenge {

    private Challenge() {
    }

    public static ChallengeBuilder create(String name){
        return new ChallengeBuilder();
    }
}

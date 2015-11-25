package fr.unice.polytech.ecoknowledge.language.api.implem;

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

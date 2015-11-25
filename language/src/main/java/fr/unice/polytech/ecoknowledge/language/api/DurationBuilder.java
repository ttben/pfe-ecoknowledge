package fr.unice.polytech.ecoknowledge.language.api;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class DurationBuilder {

    private ChallengeBuilder cb;

    public DurationBuilder(ChallengeBuilder cb) {
        this.cb = cb;
    }

    public ChallengeBuilder to(String date){
        return cb;
    }

    ChallengeBuilder getCb(){
        return cb;
    }
}

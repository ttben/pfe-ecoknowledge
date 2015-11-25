package fr.unice.polytech.ecoknowledge.language;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class ChallengeBuilder {

    public ChallengeBuilder(){

    }

    public DurationBuilder from(String date){
        return  new DurationBuilder(this);
    }

    public ChallengeBuilder during(Integer value, DURATION_TYPE type){
        return this;
    }

    public ChallengeBuilder isWorth(Integer points){
        return this;
    }

}

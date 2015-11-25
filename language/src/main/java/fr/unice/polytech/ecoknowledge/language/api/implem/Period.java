package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.interfaces.IChallengeable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IDurationnable;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class Period extends ChallengeBuilder implements IDurationnable {

    private ChallengeBuilder cb;

    private Calendar start = null;
    private Calendar end = null;

    Period(ChallengeBuilder challengeBuilder, String date) {
        start = parseDate(date, false);
        cb = challengeBuilder;
        challengeBuilder.addPeriod(this);
    }


    @Override
    public IChallengeable to(String date) {
        end = parseDate(date, true);
        return cb;
    }


    private Calendar parseDate(String d, boolean endDay){
        Calendar date = Calendar.getInstance();
        try {
            String[] args = d.split("/");
            if(args.length > 0)
                date.set(Calendar.DAY_OF_MONTH, Integer.parseInt(args[0]));
            if(args.length > 1)
                date.set(Calendar.MONTH, Integer.parseInt(args[1]) - 1);
            if(args.length > 2)
                date.set(Calendar.YEAR, Integer.parseInt(args[2]));
            if(endDay) {
                date.set(Calendar.HOUR, 23);
                date.set(Calendar.MINUTE, 59);
                date.set(Calendar.SECOND, 59);
                date.set(Calendar.MILLISECOND, 999);
            }
            else{
                date.set(Calendar.HOUR, 0);
                date.set(Calendar.MINUTE, 0);
                date.set(Calendar.SECOND, 0);
                date.set(Calendar.MILLISECOND, 0);
            }
        }catch (Throwable t){
            return null;
        }
        return date;
    }

    @Override
    public String toString() {
        return "Period{" +
                ", start=" + start.getTime().toString() +
                ", end=" + end.getTime().toString() +
                '}';
    }
}
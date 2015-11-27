package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.interfaces.IChallengeable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IDurationnable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IDuringable;
import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class Period implements IDurationnable {

    private ChallengeBuilder cb;

    private DateTime start = null;
    private DateTime end = null;

    Period(ChallengeBuilder challengeBuilder, int day) {
        start = parseDate(day, null, null, false);
        cb = challengeBuilder;
        challengeBuilder.addPeriod(this);
    }
    Period(ChallengeBuilder challengeBuilder, int day, int month) {
        start = parseDate(day, month, null, false);
        cb = challengeBuilder;
        challengeBuilder.addPeriod(this);
    }
    Period(ChallengeBuilder challengeBuilder, int day, int month, int year) {
        start = parseDate(day, month, year, false);
        cb = challengeBuilder;
        challengeBuilder.addPeriod(this);
    }

    @Override
    public IDuringable to(int day) {
        end = parseDate(day, null, null, true);
        return new During(cb);
    }
    @Override
    public IDuringable to(int day, int month) {
        end = parseDate(day, month, null, true);
        return new During(cb);
    }
    @Override
    public IDuringable to(int day, int month, int year) {
        end = parseDate(day, month, year, true);
        return new During(cb);
    }


    private DateTime parseDate(int day, Integer month, Integer year, boolean end){

        return new DateTime(
                (year==null)?Calendar.getInstance().get(Calendar.YEAR):year,
                (month==null)?Calendar.getInstance().get(Calendar.MONTH):month,
                day, end?23:0, end?59:0, end?59:0
        );
    }

    @Override
    public String toString() {
        return "Period{" +
                ", start=" + start.toString() +
                ", sendTo=" + end.toString() +
                '}';
    }

    DateTime getStart() {
        return start;
    }

    DateTime getEnd() {
        return end;
    }
}

package fr.unice.polytech.ecoknowledge.language;

import fr.unice.polytech.ecoknowledge.language.api.implem.Challenge;
import fr.unice.polytech.ecoknowledge.language.api.implem.ChallengeBuilder;
import junit.framework.Assert;
import org.joda.time.IllegalFieldValueException;
import org.junit.Test;

import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.DAY;

/**
 * Created by Sébastien on 27/11/2015.
 */
public class FailureTest {
    @Test
    public void dateFailure() {

        IllegalFieldValueException exception = null;

        try {
            ChallengeBuilder cb = Challenge.create("cb");
            cb
                    .from(48).to(2, 899, 2)
                    .during(3, DAY).rewards(-10)
                    .withConditions()
                    .valueOf("smth").greaterThan(30)
                    .end();

            System.out.println(cb.getDescription().toString(5));
        }catch (IllegalFieldValueException t){
            exception = t;
        }

        Assert.assertNotNull(exception);
    }
}

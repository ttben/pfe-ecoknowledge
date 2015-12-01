package fr.unice.polytech.ecoknowledge.language.api.implem;

import org.joda.time.IllegalFieldValueException;
import org.junit.Test;

import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.DAY;
import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.WEEK;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Sébastien on 27/11/2015.
 */
public class FAIL_DateTest {

    @Test
    public void dateFailure() {

        IllegalFieldValueException exception = null;

        try {
            Challenge.create("cb")
                    .dontSend() // Just because it's a test
                    .availableFrom(48).to(2, 899, 2)
                    .during(3, DAY).rewards(10)
                    .withConditions()
                    .valueOf("smth").greaterThan(30)
                    .end();

        }catch (IllegalFieldValueException t){
            exception = t;
        }

        assertNotNull(exception);
    }

    @Test
    public void negativePeriodFailure(){

        IllegalArgumentException exception = null;

        try{
            Challenge.create("cb")
                    .dontSend() // Just because it's a test
                    .availableFrom(23).to(2)
                    .during(2, WEEK).rewards(20)
                    .withConditions()
                    .valueOf("smth").differentFrom(2)
                    .end();
        } catch (IllegalArgumentException t){
            exception = t;
        }

        assertNotNull(exception);
    }
}

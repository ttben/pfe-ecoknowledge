package fr.unice.polytech.ecoknowledge.language.api.implem;

import junit.framework.Assert;
import org.joda.time.IllegalFieldValueException;
import org.junit.Test;

import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.DAY;

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
                    .during(3, DAY).rewards(-10)
                    .withConditions()
                    .valueOf("smth").greaterThan(30)
                    .end();

        }catch (IllegalFieldValueException t){
            exception = t;
        }

        Assert.assertNotNull(exception);
    }
}

package fr.unice.polytech.ecoknowledge.integration;

import fr.unice.polytech.ecoknowledge.calculator.producer.CalculatorProducer;
import fr.unice.polytech.ecoknowledge.calculator.worker.CalculatorWorker;
import fr.unice.polytech.ecoknowledge.feeder.producer.FeederProducer;
import fr.unice.polytech.ecoknowledge.feeder.worker.FeederWorker;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by Sébastien on 22/02/2016.
 */
public class IntegrationTest {
    public static final String NAME_OF_FEEDER_WORKER = "feederworker1";
    public static final int FEEDER_REFRESHING_FREQUENCY = 750;

    public static final String NAME_OF_CALCULATOR_WORKER = "calculator1";
    public static final int CALCULATOR_REFRESHING_FREQUENCY = 1250;

    public static void main(String[] args) {
        IntegrationTest lol = new IntegrationTest();
        lol.setUpCalculator();
        lol.setUpFeeder();
    }

    IntegrationTest() {

    }

    private void setUpFeeder() {
        FeederWorker feederWorker = new FeederWorker(NAME_OF_FEEDER_WORKER, 0);
        FeederProducer feederProducer = new FeederProducer(FEEDER_REFRESHING_FREQUENCY);

        thread(feederWorker, false);
        thread(feederProducer, false);
    }

    private void setUpCalculator() {
        CalculatorWorker calculatorWorker = new CalculatorWorker(NAME_OF_CALCULATOR_WORKER, 0);
        CalculatorProducer calculatorProducer = new CalculatorProducer(CALCULATOR_REFRESHING_FREQUENCY, -1);

        thread(calculatorWorker, false);
        thread(calculatorProducer, false);
    }

    private void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }

}

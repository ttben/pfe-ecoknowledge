package fr.unice.polytech.ecoknowledge.calculator;


import fr.unice.polytech.ecoknowledge.calculator.producer.CalculatorProducer;
import fr.unice.polytech.ecoknowledge.calculator.worker.CalculatorWorker;

public class MainTest {
	public static void main(String[] args) throws InterruptedException {
		//createProducer(10, 10, 1000);
		createWorker(10);
	}

	private static void createProducer(int nbOfProducersToCreate, int refreshFrequency, int nbOfMsgToCreateForEachProducer) {
		for(int i = 0 ; i < nbOfProducersToCreate ; i ++) {
			CalculatorProducer producer = new CalculatorProducer(refreshFrequency, nbOfMsgToCreateForEachProducer);
			thread(producer, false);
		}
	}

	private static void createWorker(int nbOfWorkersToCreate) {
		for(int i = 0 ; i < nbOfWorkersToCreate ; i ++) {
			CalculatorWorker n = new CalculatorWorker("worker1", 0);
			thread(n, false);
		}
	}

	public static void thread(Runnable runnable, boolean daemon) {
		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(daemon);
		brokerThread.start();
	}
}

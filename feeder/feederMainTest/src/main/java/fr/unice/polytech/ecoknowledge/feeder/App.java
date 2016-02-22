package fr.unice.polytech.ecoknowledge.feeder;

import fr.unice.polytech.ecoknowledge.feeder.producer.FeederProducer;
import fr.unice.polytech.ecoknowledge.feeder.worker.FeederWorker;

public class App {

	public static void main(String[] args) throws Exception {
		thread(new FeederProducer(10), false);
		thread(new FeederWorker("task2", 100), false);
		//thread(new FeederWorker("task1", 100), false);
	}

	public static void thread(Runnable runnable, boolean daemon) {
		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(daemon);
		brokerThread.start();
	}
}
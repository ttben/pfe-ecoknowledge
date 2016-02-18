package fr.unice.polytech.ecoknowledge.feeder;

import fr.unice.polytech.ecoknowledge.feeder.producer.Producer;
import fr.unice.polytech.ecoknowledge.feeder.worker.Consumer;

public class App {

	public static void main(String[] args) throws Exception {
		/*
		BrokerService broker = new BrokerService();

		// configure the broker
		broker.setBrokerName("fred");
		broker.addConnector("vm://localhost:61616");
		broker.start();
		*/

		thread(new Producer(), false);


		thread(new Consumer("task2", 100), false);
		//thread(new Consumer("task1", 100), false);

	}

	public static void thread(Runnable runnable, boolean daemon) {
		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(daemon);
		brokerThread.start();
	}
}
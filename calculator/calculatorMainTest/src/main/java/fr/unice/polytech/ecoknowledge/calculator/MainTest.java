package fr.unice.polytech.ecoknowledge.calculator;


import fr.unice.polytech.ecoknowledge.calculator.producer.CalculatorProducer;
import fr.unice.polytech.ecoknowledge.calculator.worker.CalculatorWorker;

public class MainTest {
	public static void main(String[] args) {
		CalculatorProducer producer = new CalculatorProducer(5000);
		CalculatorWorker n = new CalculatorWorker("worker1", 250);

		thread(producer, false);
		thread(n, false);
	}

	public static void thread(Runnable runnable, boolean daemon) {
		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(daemon);
		brokerThread.start();
	}
}

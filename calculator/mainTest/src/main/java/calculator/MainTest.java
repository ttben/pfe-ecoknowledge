package calculator;

import fr.unice.polytech.ecoknowledge.calculator.producer.Producer;
import fr.unice.polytech.ecoknowledge.calculator.worker.Worker;

/**
 * Created by Benjamin on 20/02/2016.
 */
public class MainTest {
	public static void main(String[] args) {
		Producer producer = new Producer(5000);
		Worker n = new Worker("worker1", 250);

		thread(producer, false);
		thread(n, false);
	}

	public static void thread(Runnable runnable, boolean daemon) {
		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(daemon);
		brokerThread.start();
	}
}

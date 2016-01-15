import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Benjamin on 14/01/2016.
 */
public class Feeder implements Runnable {

	//EcoknowledgeDataHandler ecoknowledgeDataHandler = MongoDBHandler.getInstance();
	private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

	static int number = 0;

	int current = 0;
	private ConcurrentLinkedQueue<String> targetSensor = new ConcurrentLinkedQueue<>();

	Feeder() {
		current = ++number;
	}

	Feeder(List<String> targetSensor) {
		for(String sensorName : targetSensor) {
			this.targetSensor.offer(sensorName);
		}

		current = ++number;
		executorService.scheduleWithFixedDelay(this, 0, 1, TimeUnit.SECONDS);

	}

	@Override
	public void run() {
		//System.out.printf("Thread [%d] - Running ...\n", current);

		URLConnection connection = null;
		try {
			Iterator<String> iterator = targetSensor.iterator();
			for(int i = 0 ; i < targetSensor.size() ; i ++) {
				String sensorName = iterator.next();

				URL oracle = new URL("http://localhost:8081/fakeDataSource/sensors/"+sensorName+"/data");
				BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
				String inputLine;

				String res = "";

				while ((inputLine = in.readLine()) != null) {
//System.out.println("Received" + inputLine);
					res += inputLine;
				}
				System.out.printf("Thread [%d] - Tracking sensor %s and received %s\n",current, sensorName,res);

				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void stopToTrack(String targetSensor) {
		System.out.printf("Thread [%d] - Stop tracking sensor %s\n",current, targetSensor);
		this.targetSensor.remove(targetSensor);
		if(this.targetSensor.size() == 0) {
			System.out.printf("Thread [%d] - has nothing left to track -> kill\n",current);
			kill();
		}
	}

	public void kill() {
		try {
			System.out.printf("Thread [%d] - attempt to shutdown executor\n",current);
			executorService.shutdown();
			executorService.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.err.printf("Thread [%d] - tasks interrupted\n",current);
		} finally {
			if (!executorService.isTerminated()) {
				System.err.printf("Thread [%d] - cancel non-finished tasks\n",current);
			}
			executorService.shutdownNow();
			System.out.printf("Thread [%d] - shutdown finished\n",current);
		}
	}

	public void startToTrack(String sensor) {
		System.out.printf("Thread [%d] - Start tracking sensor %s\n",current, sensor);

		this.targetSensor.offer(sensor);
		if(targetSensor.size() == 1) {
			executorService = Executors.newScheduledThreadPool(1);
			executorService.scheduleWithFixedDelay(this, 1,1, TimeUnit.SECONDS);
		}
	}
}

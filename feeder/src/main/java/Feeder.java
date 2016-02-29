import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Benjamin on 14/01/2016.
 */
public class Feeder extends Thread {

	//EcoknowledgeDataHandler ecoknowledgeDataHandler = MongoDBHandler.getInstance();

	static int number = 0;

	int current = 0;
	private ConcurrentLinkedQueue<String> targetSensor = new ConcurrentLinkedQueue<>();

	Feeder() {
		current = ++number;
		this.setName("Thread [" + current + "]");
		System.out.printf("Thread [%s] - Built\n", this.getName());

	}

	Feeder(List<String> targetSensor) {
		for (String sensorName : targetSensor) {
			this.targetSensor.offer(sensorName);
		}

		current = ++number;

	}

	@Override
	public void run() {
		//System.out.printf("Thread [%d] - Running ...\n", current);

		URLConnection connection = null;
		try {
			Iterator<String> iterator = targetSensor.iterator();
			for (int i = 0; i < targetSensor.size(); i++) {
				String sensorName = iterator.next();

				URL oracle = new URL("http://localhost:8081/fakeDataSource/sensors/" + sensorName + "/data");
				BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
				String inputLine;

				String res = "";

				while ((inputLine = in.readLine()) != null) {
//System.out.println("Received" + inputLine);
					res += inputLine;
				}
				System.out.printf("Thread [%s] - Tracking sensor %s and received %s\n", this.getName(), sensorName, res);

				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public boolean stopToTrack(String targetSensor) {
		System.out.printf("Thread [%s] - Stop tracking sensor %s\n", this.getName(), targetSensor);
		this.targetSensor.remove(targetSensor);
		if (this.targetSensor.size() == 0) {
			System.out.printf("Thread [%s] - has nothing left to track -> kill\n", this.getName());
			this.interrupt();
			return false;
		} else {
			System.out.printf("Thread [%s] - Remaining track %s\n", this.getName(), this.targetSensor);
			return true;
		}
	}

	public void startToTrack(String sensor) {
		System.out.printf("Thread [%s] - Start tracking sensor %s\n", this.getName(), sensor);
		this.targetSensor.offer(sensor);
	}
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Benjamin on 14/01/2016.
 */
public class FeederHandler {

	private Map<String, Feeder> map = new HashMap<>();

	private List<Feeder> feederList = new ArrayList<>();

	FeederHandler(int numberOfThreads) {
		for(int i = 0 ; i < numberOfThreads ; i ++) {
			feederList.add(new Feeder());
		}
	}

	void startToTrack(String targetSensor) {
		System.out.printf("Ask to start tracking %s\n", targetSensor);
		this.feederList.get(0).startToTrack(targetSensor);
		this.map.put(targetSensor, feederList.get(0));
	}

	void stopToTrack(String targetSensor) {
		System.out.printf("Ask to stop tracking %s\n", targetSensor);
		Feeder f = map.get(targetSensor);
		f.stopToTrack(targetSensor);
	}

	public void killAll() {
		for(Feeder f : feederList) {
			f.kill();
		}
	}
}

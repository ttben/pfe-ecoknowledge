import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Benjamin on 14/01/2016.
 */
public class FeederHandler {
	private ScheduledThreadPoolExecutor executorService;

	private Map<String, Feeder> map = new HashMap<>();

	private List<Feeder> feederList = new ArrayList<>();
	private List<ScheduledFuture<?>> feederFuture = new ArrayList<>();
	private int numberOfThreads;

	FeederHandler(int numberOfThreads) {
		executorService = new ScheduledThreadPoolExecutor(numberOfThreads);
		executorService.setRemoveOnCancelPolicy(true);
	}

	void startToTrack(String targetSensor) {
		System.out.printf("Ask to start tracking %s\n", targetSensor);

		Feeder targetFeeder = null;
		if (feederList.size() == 0) {
			if (executorService == null) {
				executorService = new ScheduledThreadPoolExecutor(numberOfThreads);
				executorService.setRemoveOnCancelPolicy(true);
			}

			System.out.printf("No thread currently running. Building one to start tracking...\n");
			targetFeeder = new Feeder();
			feederList.add(targetFeeder);

			targetFeeder.startToTrack(targetSensor);

			ScheduledFuture<?> scheduledFuture = executorService.scheduleWithFixedDelay(targetFeeder, 0, 1, TimeUnit.SECONDS);
			feederFuture.add(scheduledFuture);
		} else {
			targetFeeder = this.feederList.get(0);    //	retrieve first not "full"
			targetFeeder.startToTrack(targetSensor);
		}

		this.map.put(targetSensor, targetFeeder);

	}

	void stopToTrack(String targetSensor) {
		System.out.printf("Ask to stop tracking %s\n", targetSensor);

		Feeder f = map.get(targetSensor);

		if (!f.stopToTrack(targetSensor)) {
			System.out.printf("Detecting %s has nothing left to do. Lets kill him! \n", f.getName());
			int index;
			for (index = 0; index < this.feederList.size(); index++) {
				Feeder lol = feederList.get(index);

				if (lol.getName().equals(f.getName())) {
					System.out.printf("Feeder found at index %d\n", index);
					feederFuture.get(index).cancel(true);
				}
			}

			feederList.remove(index - 1);
			feederFuture.remove(index - 1);

			System.out.println("Current active count" + executorService.getActiveCount());    // returns 4 !!!!
			if (executorService.getActiveCount() == 0) {
				killAll();
			}
		}

	}

	public void killAll() {
		this.feederFuture.clear();
		this.feederList.clear();
		try {
			System.out.printf("Attempt to shutdown executor\n");
			executorService.shutdown();
			executorService.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.err.printf("Tasks interrupted\n");
		} finally {
			if (!executorService.isTerminated()) {
				System.err.printf("Cancel non-finished tasks\n");
			}
			executorService.shutdownNow();
			System.out.printf("Shutdown finished\n");
			executorService = null;
		}


	}
}

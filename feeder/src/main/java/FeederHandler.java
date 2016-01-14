import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Benjamin on 14/01/2016.
 */
public class FeederHandler {
	private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

	static int wait = 2;

	FeederHandler() {
		executorService.scheduleWithFixedDelay(new Feeder(), 0,wait, TimeUnit.SECONDS);
	}

	void addFeeder(Feeder f) {
		executorService.scheduleWithFixedDelay(f,0,wait,TimeUnit.SECONDS);
	}
}

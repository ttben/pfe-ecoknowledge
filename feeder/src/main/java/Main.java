public class Main {

    public static void main(String[] args) throws InterruptedException {
	    // write your code here
        FeederHandler f = new FeederHandler(5);
        f.startToTrack("TEMP_443");
        Thread.sleep(3000);

        f.startToTrack("DOOR_443");
        Thread.sleep(3000);

        f.stopToTrack("TEMP_443");
        Thread.sleep(3000);

        f.stopToTrack("DOOR_443");
        Thread.sleep(3000);

        f.startToTrack("DOOR_443");

        /*
        f.startToTrack("DOOR_443");
        Thread.sleep(3000);

        f.stopToTrack("TEMP_443");
        Thread.sleep(3000);

        f.stopToTrack("DOOR_443");

        Thread.sleep(3000);
        f.startToTrack("DOOR_443");
        Thread.sleep(3000);
        f.killAll();
        */
    }
}

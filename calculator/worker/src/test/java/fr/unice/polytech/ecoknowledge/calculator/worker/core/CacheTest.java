package fr.unice.polytech.ecoknowledge.calculator.worker.core;

import com.google.gson.JsonObject;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Sébastien on 11/12/2015.
 */
public class CacheTest {

	private static Cache cache;
	private static String sensor = "TEMP_443V";

	@BeforeClass
	public static void createFakeCache() {
		cache = Cache.getFakeCache();

		DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd'T'HH:mm:ss'Z'");

		JsonObject firstDay = new JsonObject();
		firstDay.addProperty("sensor", sensor);
		firstDay.addProperty("data", 1);
		firstDay.addProperty("date", new DateTime(2016, 1, 1, 8, 2, 0, 0).withDayOfWeek(1).toString(dtf));

		JsonObject secondDay = new JsonObject();
		secondDay.addProperty("sensor", sensor);
		secondDay.addProperty("data", 2);
		secondDay.addProperty("date", new DateTime(2016, 1, 2, 8, 2, 0, 0).withDayOfWeek(5).toString(dtf));

		JsonObject secondDayBis = new JsonObject();
		secondDayBis.addProperty("sensor", sensor);
		secondDayBis.addProperty("data", 3);
		secondDayBis.addProperty("date", new DateTime(2016, 1, 2, 15, 2, 0, 0).withDayOfWeek(5).toString(dtf));

		JsonObject thirdDay = new JsonObject();
		thirdDay.addProperty("sensor", sensor);
		thirdDay.addProperty("data", 4);
		thirdDay.addProperty("date", new DateTime(2016, 1, 2, 8, 2, 0, 0).withDayOfWeek(6).toString(dtf));

		cache.addData(firstDay);
		cache.addData(secondDay);
		cache.addData(secondDayBis);
		cache.addData(thirdDay);
	}

	@Test
	public void checkDate() {

		List<Data> data = cache.getDataOfSensorBetweenDate(sensor, new DateTime(2015, 12, 28, 0, 0, 0, 0), new DateTime(2016, 1, 2, 8, 2, 0, 0));
		assertEquals(3, data.size());
	}

	@Test
	public void checkFilter() {
		List<Data> data = cache.getDataOfSensorBetweenDate(sensor, new DateTime(2015, 12, 28, 0, 0, 0, 0), new DateTime(2016, 1, 2, 8, 2, 0, 0),
				new AbstractMap.SimpleEntry<Integer, Integer>(3, 5),
				Arrays.asList(new AbstractMap.SimpleEntry<Integer, Integer>(8, 11)));
		assertEquals(1, data.size());
		assertEquals(2.0, data.get(0).getValue());
	}


}

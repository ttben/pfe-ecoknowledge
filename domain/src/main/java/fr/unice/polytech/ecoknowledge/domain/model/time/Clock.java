package fr.unice.polytech.ecoknowledge.domain.model.time;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.TimeZone;

/**
 * Created by Sébastien on 01/12/2015.
 */
public class Clock {

	public Clock() {
	}

	private TimeZone middleWareTZ = TimeZone.getTimeZone("Europe/Paris");

	public DateTime parseDate(String date) {

		DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd'T'HH:mm:ss'Z'");
		DateTime dt = dtf.parseDateTime(date);

		System.out.println("DATE BEFORE :\t" + dt.toString());

		dt = createDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(),
				dt.getHourOfDay(), dt.getMinuteOfHour(), dt.getSecondOfMinute());

		System.out.println("DATE AFTER :\t" + dt.toString() + "\n");

		return dt;
	}

	public DateTime createDate(DateTime time) {
		return createDate(time.getYear(), time.getMonthOfYear(), time.getDayOfMonth(),
				time.getHourOfDay(), time.getMinuteOfHour(), time.getSecondOfMinute());
	}

	public DateTime createDate(int year, int month, int day, int hour, int minute, int second) {
		DateTime date = new DateTime(year, month, day, hour, minute, second, 0,
				DateTimeZone.forTimeZone(middleWareTZ));
		return date;
	}

	public void setMiddleWareTZ(TimeZone middleWareTZ) {
		this.middleWareTZ = middleWareTZ;
	}
}

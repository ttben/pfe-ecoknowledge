package fr.unice.polytech.ecoknowledge.domain.model.time;

import org.joda.time.DateTime;

/**
 * Created by Sébastien on 03/12/2015.
 */
public class TimeSpanGenerator {


	public static TimeBox generateTimeSpan(Recurrence recurrence, Clock clock) {

		switch (recurrence.getRecurrenceType()) {
			case DAY:
				return generateTimeSpanForDay(recurrence.getUnit(), clock);
			case WEEK:
				return generateTimeSpanForWeek(recurrence.getUnit(), clock);
			default:
				return generateTimeSpanForMonth(recurrence.getUnit(), clock);
		}
	}

	private static TimeBox generateTimeSpanForDay(Integer notUsed, Clock clock) {

		DateTime start;
		DateTime end;

		start = clock.createDate(DateTime.now()
				.withTime(0, 0, 0, 0));
		end = clock.createDate(DateTime.now()
				.withTime(23, 59, 59, 99));

		return new TimeBox(start, end);
	}

	private static TimeBox generateTimeSpanForWeek(Integer unit, Clock clock) {

		DateTime start;
		DateTime end;

		start = clock.createDate(DateTime.now().withDayOfWeek(1)
				.withTime(0, 0, 0, 0));
		end = clock.createDate(start.plusWeeks(unit - 1).withDayOfWeek(7)
				.withTime(23, 59, 59, 99));

		return new TimeBox(start, end);
	}

	private static TimeBox generateTimeSpanForMonth(Integer unit, Clock clock) {

		DateTime start;
		DateTime end;

		start = clock.createDate(DateTime.now().withDayOfWeek(1)
				.withTime(0, 0, 0, 0));
		end = clock.createDate(start.plusMonths(unit).withDayOfMonth(1).minusDays(1)
				.withTime(23, 59, 59, 99));

		return new TimeBox(start, end);
	}
}

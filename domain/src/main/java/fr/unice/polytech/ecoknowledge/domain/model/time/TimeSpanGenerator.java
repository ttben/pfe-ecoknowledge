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

		start = clock.getTime()
				.withTime(0, 0, 0, 0);
		end = clock.getTime()
				.withTime(23, 59, 59, 99);

		return new TimeBox(start, end);
	}

	private static TimeBox generateTimeSpanForWeek(Integer unit, Clock clock) {

		DateTime start;
		DateTime end;

		start = clock.getTime().withDayOfWeek(1)
				.withTime(0, 0, 0, 0);
		end = clock.createDate(start.plusWeeks(unit - 1).withDayOfWeek(7)
				.withTime(23, 59, 59, 99));

		return new TimeBox(start, end);
	}

	private static TimeBox generateTimeSpanForMonth(Integer unit, Clock clock) {

		DateTime start;
		DateTime end;

		start = clock.getTime().withDayOfWeek(1)
				.withTime(0, 0, 0, 0);
		end = clock.createDate(start.plusMonths(unit).withDayOfWeek(1).minusDays(1)
				.withTime(23, 59, 59, 99));

		return new TimeBox(start, end);
	}


	public static TimeBox generateNextTimeSpan(Recurrence recurrence, Clock clock, TimeBox next) {
		switch (recurrence.getRecurrenceType()) {
			case DAY:
				return generateTimeSpanForDay(recurrence.getUnit(), clock, next);
			case WEEK:
				return generateTimeSpanForWeek(recurrence.getUnit(), clock, next);
			default:
				return generateTimeSpanForMonth(recurrence.getUnit(), clock, next);
		}
	}

	private static TimeBox generateTimeSpanForDay(Integer unit, Clock clock, TimeBox next) {

		DateTime start;
		DateTime end;

		int nextDay = clock.createDate(next.getEnd()).getDayOfWeek();
		if(nextDay > 5)
			start = clock.createDate(next.getEnd().plusWeeks(1).withDayOfWeek(1)
					.withTime(0, 0, 0, 0));
		else
			start = clock.createDate(next.getEnd().plusDays(1)
					.withTime(0, 0, 0, 0));

		end = clock.getTime()
				.withTime(23, 59, 59, 99);

		return new TimeBox(start, end);
	}

	private static TimeBox generateTimeSpanForWeek(Integer unit, Clock clock, TimeBox next) {

		DateTime start;
		DateTime end;

		start = clock.createDate(next.getEnd().plusWeeks(1).withDayOfWeek(1)
				.withTime(0, 0, 0, 0));

		end = clock.createDate(start.plusWeeks(unit - 1).withDayOfWeek(7)
				.withTime(23, 59, 59, 99));


		return new TimeBox(start, end);
	}

	private static TimeBox generateTimeSpanForMonth(Integer unit, Clock clock, TimeBox next) {

		DateTime start;
		DateTime end;

		start = clock.createDate(next.getEnd().plusWeeks(1).withDayOfWeek(1)
				.withTime(0, 0, 0, 0));

        end = clock.createDate(start.plusMonths(unit).withDayOfWeek(1).minusDays(1)
                .withTime(23, 59, 59, 99));

		return new TimeBox(start, end);
	}


}

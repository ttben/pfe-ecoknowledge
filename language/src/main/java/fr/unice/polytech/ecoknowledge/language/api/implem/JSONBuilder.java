package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.AT_LEAST_TYPE;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DAY_MOMENT;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.WEEK_PERIOD;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Sébastien on 26/11/2015.
 */
public class JSONBuilder {
	public static JSONObject parse(ChallengeBuilder cb) {
		JSONObject challenge = new JSONObject();

		challenge.put("name", cb.getName());
		challenge.put("lifeSpan", parsePeriod(cb.getP()));
		challenge.put("recurrence", parseRecurrence(cb));
		challenge.put("levels", createLevels(cb));
		challenge.put("image", cb.getIcon() == null ? "" : cb.getIcon());

		return challenge;
	}

	private static JSONObject parseRecurrence(ChallengeBuilder cb) {
		JSONObject period = new JSONObject();

		period.put("type", cb.getType().toString());
		period.put("unit", cb.getTime());

		return period;
	}

	private static JSONObject parsePeriod(Period p) {
		JSONObject period = new JSONObject();

		period.put("start", getStringFromDateTime(p.getStart()));
		period.put("end", getStringFromDateTime(p.getEnd()));

		return period;
	}

	private static String getStringFromDateTime(DateTime time) {

		Date d = time.toDate();

		SimpleDateFormat day =
				new SimpleDateFormat("YYYY-MM-dd");
		SimpleDateFormat hour =
				new SimpleDateFormat("HH:mm:ss");

		String dayS = day.format(d);
		String hourS = hour.format(d);

		return dayS + "T" + hourS + "Z";
	}


	private static JSONArray createLevels(ChallengeBuilder cb) {
		JSONArray levels = new JSONArray();

		for (Level l : cb.getLevels()) {
			levels.put(createLevel(l));
		}

		return levels;
	}

	private static JSONObject createLevel(Level l) {
		JSONObject level = new JSONObject();

		level.put("name", l.getName());
		level.put("badge", createBadge(l));
		level.put("conditions", parseConditions(l.getConditions(), l.getImprovements()));

		return level;
	}

	private static JSONObject createBadge(Level l) {
		JSONObject badge = new JSONObject();

		badge.put("name", l.getName());
		badge.put("reward", l.getPoints());
		badge.put("image", l.getImage() == null ? "" : l.getImage());

		return badge;
	}

	private static JSONArray parseConditions(List<Condition> conditions, List<Improvement> improvements) {
		JSONArray conditionsA = new JSONArray();

		for (Condition c : conditions) {
			conditionsA.put(parseStandardCondition(c));
		}

		for (Improvement i : improvements) {
			conditionsA.put(parseImprovementCondition(i));
		}

		return conditionsA;
	}

	private static JSONObject parseImprovementCondition(Improvement i) {
		JSONObject improvement = new JSONObject();

		improvement.put("type", "improve");
		improvement.put("improvementType", i.getType().toString());
		improvement.put("threshold", i.getImprovementValue());
		improvement.put("symbolicName", i.getSensor());
		improvement.put("referencePeriod", i.getImprovementPeriod().toString());

		return improvement;
	}

	private static JSONObject parseStandardCondition(Condition c) {
		JSONObject condition = new JSONObject();

		condition.put("type", c.getType().toString());
		condition.put("expression", parseExpression(c));
		condition.put("targetTime", parseTargetTime(c.getWfv()));
		condition.put("counter", parseAtLeast(c.getWfv()));

		return condition;
	}

	private static JSONObject parseTargetTime(WaitForValue wfv) {
		JSONObject targetTime = new JSONObject();

		if (wfv == null) {

			targetTime.put("hours", DAY_MOMENT.ALL.toString());
			targetTime.put("days", WEEK_PERIOD.ALWAYS.toString());
		} else {
			targetTime.put("hours", wfv.getMoment().toString());
			targetTime.put("days", wfv.getPeriod().toString());
		}

		return targetTime;
	}

	private static JSONObject parseExpression(Condition c) {
		JSONObject expression = new JSONObject();

		JSONObject leftOperand = new JSONObject();
		leftOperand.put("symbolicName", c.getSensor());

		JSONObject rightOperand = new JSONObject();
		rightOperand.put("value", c.getValue());

		JSONObject comparator = new JSONObject();
		comparator.put("type", c.getComparator());

		expression.put("leftOperand", leftOperand);
		expression.put("rightOperand", rightOperand);
		expression.put("comparator", comparator);

		return expression;
	}

	private static JSONObject parseAtLeast(WaitForValue w) {
		JSONObject counter = new JSONObject();

		if (w == null || w.getAtLeast() == null) {
			counter.put("threshold", 100);
			counter.put("type", AT_LEAST_TYPE.PERCENT.toString());
		} else {
			counter.put("threshold", w.getAtLeast());
			counter.put("type", w.getType().toString());
		}

		return counter;
	}

}


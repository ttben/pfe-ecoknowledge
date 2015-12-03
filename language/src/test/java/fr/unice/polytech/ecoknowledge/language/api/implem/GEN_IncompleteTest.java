package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.AT_LEAST_TYPE;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DAY_MOMENT;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.WEEK_PERIOD;
import fr.unice.polytech.ecoknowledge.language.api.implem.util.JsonSearcher;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.WEEK;
import static org.junit.Assert.assertEquals;

/**
 * Created by Sébastien on 30/11/2015.
 */
public class GEN_IncompleteTest {

	JSONObject description;

	@Before
	public void createDescription() {
		ChallengeBuilder cb = Challenge.create("Without period");
		cb
				.dontSend() // Just because it's a test
				.availableFrom(2)
				.to(4)
				.during(3, WEEK)
				.atLevel("level")
				.rewards(1)
				.withConditions()
				.valueOf("SENSOR").greaterThan(2)
				.end();

		description = cb.getDescription();
	}

	@Test
	public void withoutPeriod() {

		ArrayList<Map.Entry<Object, Class>> wanted = new ArrayList<>();
		wanted.add(new AbstractMap.SimpleEntry<>("levels", JSONArray.class));
		wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
		wanted.add(new AbstractMap.SimpleEntry<>("conditions", JSONArray.class));
		wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
		wanted.add(new AbstractMap.SimpleEntry<>("counter", JSONObject.class));

		Object c = JsonSearcher.lookFor(description, wanted);
		JSONObject j = (JSONObject) c;

		assertEquals(100, j.getInt("threshold"));
		assertEquals(AT_LEAST_TYPE.PERCENT.toString(), j.getString("type"));

	}


	@Test
	public void checkTargetTime() {
		ArrayList<Map.Entry<Object, Class>> wanted = new ArrayList<>();
		wanted.add(new AbstractMap.SimpleEntry<>("levels", JSONArray.class));
		wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
		wanted.add(new AbstractMap.SimpleEntry<>("conditions", JSONArray.class));
		wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
		wanted.add(new AbstractMap.SimpleEntry<>("targetTime", JSONObject.class));

		Object t = JsonSearcher.lookFor(description, wanted);
		JSONObject targetTime = (JSONObject) t;

		assertEquals(WEEK_PERIOD.ALL.toString(), targetTime.getString("days"));
		assertEquals(DAY_MOMENT.ALL.toString(), targetTime.getString("hours"));

	}

}

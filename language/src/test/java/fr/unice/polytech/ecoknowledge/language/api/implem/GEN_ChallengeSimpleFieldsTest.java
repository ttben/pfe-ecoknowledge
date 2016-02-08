package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.util.JsonSearcher;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.WEEK;
import static org.junit.Assert.assertEquals;

/**
 * Created by Sébastien on 27/11/2015.
 */
public class GEN_ChallengeSimpleFieldsTest {

	JSONObject description;

	@Before
	public void createChallenge() {

		Challenge c = Challenge.create("DSL done")
				.availableFrom(23, 11, 2015).to(7, 3, 2016)
				.repeatEvery(1, WEEK)
				.addLevel("level")
				.rewards(2)
				.withConditions()
				.valueOf("BENNI_RAGE_QUIT").lowerThan(1).atLeast(5).times()
				.endChallenge();

		description = c.getDescription();
	}

	@Test
	public void checkRecurrence() {

		ArrayList<Map.Entry<Object, Class>> wanted = new ArrayList<>();
		wanted.add(new AbstractMap.SimpleEntry<>("recurrence", JSONObject.class));

		Object rec = JsonSearcher.lookFor(description, wanted);
		JSONObject recurrence = (JSONObject) rec;
		assertEquals(WEEK.toString(), recurrence.getString("type"));
		assertEquals(1, recurrence.getInt("unit"));
	}

	@Test
	public void checkStartDate() {

		ArrayList<Map.Entry<Object, Class>> wanted = new ArrayList<>();
		wanted.add(new AbstractMap.SimpleEntry<>("lifeSpan", JSONObject.class));
		wanted.add(new AbstractMap.SimpleEntry<>("start", String.class));

		Object d = JsonSearcher.lookFor(description, wanted);
		String date = (String) d;
		assertEquals("2015-11-23T00:00:00Z", date);
	}

	@Test
	public void checkEndDate() {

		ArrayList<Map.Entry<Object, Class>> wanted = new ArrayList<>();
		wanted.add(new AbstractMap.SimpleEntry<>("lifeSpan", JSONObject.class));
		wanted.add(new AbstractMap.SimpleEntry<>("end", String.class));

		Object d = JsonSearcher.lookFor(description, wanted);
		String date = (String) d;
		assertEquals("2016-03-07T23:59:59Z", date);
	}

	@Test
	public void checkName() {

		ArrayList<Map.Entry<Object, Class>> wanted = new ArrayList<>();
		wanted.add(new AbstractMap.SimpleEntry<>("name", String.class));

		Object n = JsonSearcher.lookFor(description, wanted);
	}

}

package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.util.JsonSearcher;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE.WEEK;
import static org.junit.Assert.assertEquals;

/**
 * Created by Sébastien on 30/11/2015.
 */
public class GEN_ComparatorsTest {

	@Test
	public void comparatorLowerThan() {

		ChallengeBuilder cb = Challenge.create("comparatorLT");
		cb
				.dontSend() // Just because it's a test
				.availableFrom(1).to(2)
				.during(2, WEEK)
				.atLevel("level")
				.rewards(2)
				.withConditions()
				.valueOf("Sensor").lowerThan(5)
				.end();

		String comparator = getComparatorForSimpleChallenge(cb.getDescription());
		assertEquals("<", comparator);

	}

	@Test
	public void comparatorGreaterThan() {

		ChallengeBuilder cb = Challenge.create("comparatorGT");
		cb
				.dontSend() // Just because it's a test
				.availableFrom(1).to(2)
				.during(2, WEEK)
				.atLevel("level")
				.rewards(2)
				.withConditions()
				.valueOf("Sensor").greaterThan(5)
				.end();

		String comparator = getComparatorForSimpleChallenge(cb.getDescription());
		assertEquals(">", comparator);

	}

	@Test
	public void comparatorEqualsTo() {

		ChallengeBuilder cb = Challenge.create("comparatorLT");
		cb
				.dontSend() // Just because it's a test
				.availableFrom(1).to(2)
				.during(2, WEEK)
				.atLevel("level")
				.rewards(2)
				.withConditions()
				.valueOf("Sensor").equalsTo(5)
				.end();

		String comparator = getComparatorForSimpleChallenge(cb.getDescription());
		assertEquals("=", comparator);

	}

	@Test
	public void comparatorDifferentFrom() {

		ChallengeBuilder cb = Challenge.create("comparatorDF");
		cb
				.dontSend() // Just because it's a test
				.availableFrom(1).to(2)
				.during(2, WEEK)
				.atLevel("level")
				.rewards(2)
				.withConditions()
				.valueOf("Sensor").differentFrom(5)
				.end();

		String comparator = getComparatorForSimpleChallenge(cb.getDescription());
		assertEquals("!=", comparator);

	}

	private String getComparatorForSimpleChallenge(JSONObject json) {

		ArrayList<Map.Entry<Object, Class>> wanted = new ArrayList<>();
		wanted.add(new AbstractMap.SimpleEntry<>("levels", JSONArray.class));
		wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
		wanted.add(new AbstractMap.SimpleEntry<>("conditions", JSONArray.class));
		wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
		wanted.add(new AbstractMap.SimpleEntry<>("expression", JSONObject.class));
		wanted.add(new AbstractMap.SimpleEntry<>("comparator", JSONObject.class));
		wanted.add(new AbstractMap.SimpleEntry<>("type", String.class));


		Object c = JsonSearcher.lookFor(json, wanted);
		return (String) c;
	}

}

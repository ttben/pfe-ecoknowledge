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

		Challenge c = Challenge.create("comparatorLT")
				.availableFrom(1, 10, 2020).to(2, 10, 2020)
				.repeatEvery(2, WEEK)
				.addLevel("level")
				.rewards(2)
				.withConditions()
				.valueOf("Sensor").lowerThan(5)
				.endChallenge();

		String comparator = getComparatorForSimpleChallenge(c.getDescription());
		assertEquals("<", comparator);

	}

	@Test
	public void comparatorGreaterThan() {

		Challenge c = Challenge.create("comparatorGT")
				.availableFrom(1, 1, 2016).to(2, 2, 2016)
				.repeatEvery(2, WEEK)
				.addLevel("level")
				.rewards(2)
				.withConditions()
				.valueOf("Sensor").greaterThan(5)
				.endChallenge();

		String comparator = getComparatorForSimpleChallenge(c.getDescription());
		assertEquals(">", comparator);

	}

	@Test
	public void comparatorEqualsTo() {

		Challenge c = Challenge.create("comparatorLT")
				.availableFrom(1, 10, 2020).to(2, 10, 2020)
				.repeatEvery(2, WEEK)
				.addLevel("level")
				.rewards(2)
				.withConditions()
				.valueOf("Sensor").equalsTo(5)
				.endChallenge();

		String comparator = getComparatorForSimpleChallenge(c.getDescription());
		assertEquals("=", comparator);

	}

	@Test
	public void comparatorDifferentFrom() {

		Challenge c = Challenge.create("comparatorDF")
				.availableFrom(1, 10, 2020).to(2, 10, 2020)
				.repeatEvery(2, WEEK)
				.addLevel("level")
				.rewards(2)
				.withConditions()
				.valueOf("Sensor").differentFrom(5)
				.endChallenge();

		String comparator = getComparatorForSimpleChallenge(c.getDescription());
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

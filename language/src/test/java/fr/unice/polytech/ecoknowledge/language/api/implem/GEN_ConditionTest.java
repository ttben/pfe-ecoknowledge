package fr.unice.polytech.ecoknowledge.language.api.implem;

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
import static fr.unice.polytech.ecoknowledge.language.api.implem.enums.OLD_PERIOD.LAST_MONTH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Sébastien on 30/11/2015.
 */
public class GEN_ConditionTest {

	JSONObject description;

	@Before
	public void createChallenge() {

		Challenge c = Challenge.create("DSL done")
				.availableFrom(23, 11, 2015).to(7, 3, 2016)
				.repeatEvery(1, WEEK)
				.addLevel("level")
				.rewards(2)
				.withConditions()
				.valueOf("BENNI_RAGE_QUIT").lowerThan(1)
				.on(WEEK_PERIOD.WEEK_DAYS, DAY_MOMENT.MORNING)
				.atLeast(5).times()
				.and()
				.increase("SENSOR").by(50).percent().comparedTo(LAST_MONTH)
				.endChallenge();

		description = c.getDescription();
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

		assertEquals(WEEK_PERIOD.WEEK_DAYS.toString(), targetTime.getString("days"));
		assertEquals(DAY_MOMENT.MORNING.toString(), targetTime.getString("hours"));

	}

	@Test
	public void checkOperands() {

		ArrayList<Map.Entry<Object, Class>> wanted = new ArrayList<>();
		wanted.add(new AbstractMap.SimpleEntry<>("levels", JSONArray.class));
		wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
		wanted.add(new AbstractMap.SimpleEntry<>("conditions", JSONArray.class));
		wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
		wanted.add(new AbstractMap.SimpleEntry<>("expression", JSONObject.class));

		Object e = JsonSearcher.lookFor(description, wanted);
		JSONObject expression = (JSONObject) e;
		assertNotNull(expression.getJSONObject("comparator").getString("type"));
		assertNotNull(expression.getJSONObject("leftOperand"));
		assertNotNull(expression.getJSONObject("leftOperand").getString("symbolicName"));
		assertNotNull(expression.getJSONObject("rightOperand"));
		assertNotNull(expression.getJSONObject("rightOperand").getInt("value"));
	}

	@Test
	public void checkImprovement() {

		ArrayList<Map.Entry<Object, Class>> wanted = new ArrayList<>();
		wanted.add(new AbstractMap.SimpleEntry<>("levels", JSONArray.class));
		wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
		wanted.add(new AbstractMap.SimpleEntry<>("conditions", JSONArray.class));
		wanted.add(new AbstractMap.SimpleEntry<>(1, JSONObject.class));

		Object i = JsonSearcher.lookFor(description, wanted);
		JSONObject improvement = (JSONObject) i;
		
		assertEquals(IMPROVEMENT_TYPE.INCREASE.toString(), improvement.getString("improvementType"));
		assertEquals(LAST_MONTH.toString(), improvement.getString("referencePeriod"));
		assertEquals("SENSOR", improvement.getString("symbolicName"));
		assertEquals(50, improvement.getInt("threshold"));

	}
}

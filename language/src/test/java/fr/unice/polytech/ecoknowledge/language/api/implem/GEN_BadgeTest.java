package fr.unice.polytech.ecoknowledge.language.api.implem;

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
public class GEN_BadgeTest {

	JSONObject description;

	@Before
	public void createChallenge() {

		Challenge c = Challenge.create("DSL done")
				.withIcon("http://d75822.medialib.glogster.com/media/dc/" +
						"dcc6f991541e0ea8462e6d3de93f9f69e4a5d43be170ec4b25fdc113549843b0/happy-jpg.jpg")
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
	public void checkIcon() {

		ArrayList<Map.Entry<Object, Class>> wanted = new ArrayList<>();
		wanted.add(new AbstractMap.SimpleEntry<>("image", String.class));

		Object i = JsonSearcher.lookFor(description, wanted);
		String icon = (String) i;

		assertEquals("http://d75822.medialib.glogster.com/media/dc/dcc6f991541e0ea8462e6d3de93f9f69e4a5d43be170ec4b25fdc113549843b0/happy-jpg.jpg",
				icon);
	}

	@Test
	public void checkName() {

		ArrayList<Map.Entry<Object, Class>> wanted = new ArrayList<>();
		wanted.add(new AbstractMap.SimpleEntry<>("levels", JSONArray.class));
		wanted.add(new AbstractMap.SimpleEntry<>(0, JSONObject.class));
		wanted.add(new AbstractMap.SimpleEntry<>("badge", JSONObject.class));
		wanted.add(new AbstractMap.SimpleEntry<>("name", String.class));

		Object r = JsonSearcher.lookFor(description, wanted);
	}

}

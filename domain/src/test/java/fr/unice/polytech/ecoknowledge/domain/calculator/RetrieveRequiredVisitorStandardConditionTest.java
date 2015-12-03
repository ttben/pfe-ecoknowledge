package fr.unice.polytech.ecoknowledge.domain.calculator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.domain.TestUtils;
import fr.unice.polytech.ecoknowledge.domain.model.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.TimeBox;
import fr.unice.polytech.ecoknowledge.domain.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;

@RunWith(MockitoJUnitRunner.class)
public class RetrieveRequiredVisitorStandardConditionTest {

	private Challenge challenge;

	private TimeBox lifeSpan;

	@Mock
	private User user;

	private Goal goal;

	private JsonObject jsonObject;

	private String aSensorName = "TEMP_4442";
	private String anotherSensorName = "TEMP_7842";

	@Before
	public void setUp() throws Exception {

		Map<String, String> fakedSymbolicNameToSensorNamesMap = new HashMap<>();
		fakedSymbolicNameToSensorNamesMap.put("TMP_CLI", aSensorName);
		fakedSymbolicNameToSensorNamesMap.put("TMP_AMB", anotherSensorName);
		willReturn(fakedSymbolicNameToSensorNamesMap).given(user).getSymbolicNameToSensorNameMap();

		jsonObject = TestUtils.getFakeJson(1);

		ObjectMapper objectMapper = new ObjectMapper();
		challenge = (Challenge) objectMapper.readValue(jsonObject.toString(), Challenge.class);
		lifeSpan = challenge.getTimeSpan();

		goal = new Goal(null, challenge, lifeSpan, user);
	}

	@Test
	public void theVisitor_WhenVisitAGoal_ShouldRetrieveProperSensorNames() throws Exception {
		RetrieveRequiredVisitor visitor = new RetrieveRequiredVisitor(goal);

		goal.accept(visitor);
		Map<String, TimeBox> result = visitor.getResult();

		assertTrue(result.containsKey(aSensorName));
		assertTrue(result.containsKey(anotherSensorName));
	}

	@Test
	public void theVisitor_WhenVisitAGoal_ShouldRetrieveProperTimeBoxes() throws Exception {
		RetrieveRequiredVisitor visitor = new RetrieveRequiredVisitor(goal);

		goal.accept(visitor);
		Map<String, TimeBox> result = visitor.getResult();
		TimeBox firstTimeBox = result.get(aSensorName);
		TimeBox secondTimeBox = result.get(anotherSensorName);

		assertEquals(lifeSpan, firstTimeBox);
		assertEquals(lifeSpan, secondTimeBox);
	}
}
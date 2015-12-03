package fr.unice.polytech.ecoknowledge.domain.calculator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.TestUtils;
import fr.unice.polytech.ecoknowledge.domain.model.*;
import fr.unice.polytech.ecoknowledge.domain.views.goals.ConditionResult;
import fr.unice.polytech.ecoknowledge.domain.views.goals.GoalResult;
import fr.unice.polytech.ecoknowledge.domain.views.goals.LevelResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class AchievementProcessorVisitorStandardConditionTest {

	@Mock
	private Cache cache;

	private Challenge challenge;

	private TimeBox lifeSpan;

	@Mock
	private User user;

	private Goal goal;

	private JsonObject jsonObject;

	private String aSensorName = "TEMP_4442";
	private String anotherSensorName = "TEMP_7842";

	private GoalResult goalResult;

	@Before
	public void setUp() throws IOException {

		Map<String, String> fakedSymbolicNameToSensorNamesMap = new HashMap<>();
		fakedSymbolicNameToSensorNamesMap.put("TMP_CLI", aSensorName);
		fakedSymbolicNameToSensorNamesMap.put("TMP_AMB", anotherSensorName);
		willReturn(fakedSymbolicNameToSensorNamesMap).given(user).getSymbolicNameToSensorNameMap();

		jsonObject = TestUtils.getFakeJson(1);

		ObjectMapper objectMapper = new ObjectMapper();
		challenge = (Challenge) objectMapper.readValue(jsonObject.toString(), Challenge.class);
		lifeSpan = challenge.getTimeSpan();

		goal = new Goal(null, challenge, lifeSpan, user);

		List<Data> firstSensorFakedData = new ArrayList<>();
		firstSensorFakedData.add(new Data(20.0, lifeSpan.getStart().plusDays(3)));
		firstSensorFakedData.add(new Data(28.0, lifeSpan.getStart().plusDays(5)));

		List<Data> secondSensorFakedData = new ArrayList<>();
		secondSensorFakedData.add(new Data(60.0, lifeSpan.getStart().plusDays(3)));
		secondSensorFakedData.add(new Data(25.0, lifeSpan.getStart().plusDays(5)));

		willReturn(firstSensorFakedData).given(cache).getDataOfSensorBetweenDate(Matchers.matches(aSensorName), any(), any(), any(), any());
		willReturn(secondSensorFakedData).given(cache).getDataOfSensorBetweenDate(Matchers.matches(anotherSensorName),  any(), any(), any(), any());

		AchievementProcessor achievementProcessor = new AchievementProcessor(goal, cache);
		goal.accept(achievementProcessor);
		goalResult = achievementProcessor.getGoalResult();
	}

	@Test
	public void aProcessor_WhenProcessGoal_ShouldHaveReturnProperGoalDescription() {
		assertFalse(goalResult.isAchieved());
		assertEquals(75.0, goalResult.getCorrectRate());
	}

	@Test
	public void aProcessor_WhenProcessGoal_ShouldHaveReturnProperNumberOfLevelsDescription() {
		List<LevelResult> levelResults = goalResult.getLevelResultList();
		assertEquals(1, levelResults.size());
	}

	@Test
	public void aProcessor_WhenProcessGoal_ShouldHaveReturnProperLevelStatusDescription() {
		List<LevelResult> levelResults = goalResult.getLevelResultList();
		LevelResult firstLevel = levelResults.get(0);

		assertFalse(firstLevel.isAchieved());
	}

	@Test
	public void aProcessor_WhenProcessGoal_ShouldHaveReturnProperLevelCorrectRateDescription() {
		List<LevelResult> levelResults = goalResult.getLevelResultList();
		LevelResult firstLevel = levelResults.get(0);

		assertEquals(75.0, firstLevel.getCorrectRate());
	}

	@Test
	public void aProcessor_WhenProcessGoal_ShouldHaveReturnProperNumberOfConditionsForALevel() {
		List<LevelResult> levelResults = goalResult.getLevelResultList();
		LevelResult firstLevel = levelResults.get(0);
		List<ConditionResult> conditionResults = firstLevel.getConditionResultList();

		assertEquals(2, conditionResults.size());
	}

	@Test
	public void aProcessor_WhenProcessGoal_ShouldHaveReturnProperFirstConditionDescriptionForALevel() {
		List<LevelResult> levelResults = goalResult.getLevelResultList();
		LevelResult firstLevel = levelResults.get(0);

		List<ConditionResult> conditionResults = firstLevel.getConditionResultList();
		ConditionResult firstConditionResult = conditionResults.get(0);

		assertTrue(firstConditionResult.isAchieved());
		assertEquals(100.0, firstConditionResult.getCorrectRate());
	}

	@Test
	public void aProcessor_WhenProcessGoal_ShouldHaveReturnProperSecondConditionDescriptionForALevel() {
		List<LevelResult> levelResults = goalResult.getLevelResultList();
		LevelResult firstLevel = levelResults.get(0);

		List<ConditionResult> conditionResults = firstLevel.getConditionResultList();
		ConditionResult secondConditionResult = conditionResults.get(1);

		assertFalse(secondConditionResult.isAchieved());
		assertEquals(50.0, secondConditionResult.getCorrectRate());
	}
}

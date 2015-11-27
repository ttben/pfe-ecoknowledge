package fr.unice.polytech.ecoknowledge.domain.calculator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.domain.model.Challenge;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.model.TimeBox;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class RetrieveRequiredVisitorStandardConditionTest {

	private Challenge challenge;

	@Mock
	private TimeBox lifeSpan;

	private Goal goal;

	private JsonObject jsonObject;


	@Before
	public void setUp() throws Exception {
		BufferedReader br = null;
		String result = "";

		try {

			String currentLine;

			br = new BufferedReader(new FileReader("./src/test/java/fr/unice/polytech/ecoknowledge/domain/calculator/challenge-example-sample1.json"));

			while ((currentLine = br.readLine()) != null) {
				result = result.concat(currentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		jsonObject = new JsonParser().parse(result).getAsJsonObject();

		ObjectMapper objectMapper = new ObjectMapper();
		challenge = (Challenge) objectMapper.readValue(jsonObject.toString(), Challenge.class);
		goal = new Goal(challenge, lifeSpan);
	}

	@Test
	public void testEvaluateCondition() throws Exception {

	}
}
package fr.unice.polytech.ecoknowledge.language;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression.Comparator;
import fr.unice.polytech.ecoknowledge.domain.model.conditions.basic.expression.Expression;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ExpressionTest {

	JsonObject jsonExpression = new JsonObject();
	JsonObject jsonLeftValueOperand = new JsonObject();
	JsonObject jsonRightValueOperand = new JsonObject();
	JsonObject jsonComparator = new JsonObject();

	String symbolicName = "TMP_CLI";
	Double value = 20.0;
	String comparator = ">";

	@Before
	public void setUp() {
		jsonLeftValueOperand.addProperty("symbolicName", symbolicName);
		jsonRightValueOperand.addProperty("value", value);
		jsonComparator.addProperty("type", comparator);

		jsonExpression.add("leftOperand", jsonLeftValueOperand);
		jsonExpression.add("rightOperand", jsonRightValueOperand);
		jsonExpression.add("comparator", jsonComparator);
	}

	@Test
	public void anExpression_ShouldBuildProperlyWithNoSymbolicName() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Object result = objectMapper.readValue(jsonExpression.toString(), Expression.class);
		assertNotNull(result);
	}

	@Test
	public void anExpression_WhenBuilt_ShouldHaveProperTypeOfLeftOperand() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Expression result = objectMapper.readValue(jsonExpression.toString(), Expression.class);
		assertTrue(result.getLeftOperand().getSymbolicName() instanceof String);
	}

	@Test
	public void anExpression_WhenBuilt_ShouldHaveProperValueOfLeftOperand() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Expression result = objectMapper.readValue(jsonExpression.toString(), Expression.class);
		assertEquals(symbolicName, result.getLeftOperand().getSymbolicName());
	}

	@Test
	public void anExpression_WhenBuilt_ShouldHaveProperTypeOfRightOperand() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Expression result = objectMapper.readValue(jsonExpression.toString(), Expression.class);
		assertTrue(result.getRightOperand().getValue() instanceof Double);
	}

	@Test
	public void anExpression_WhenBuilt_ShouldHaveProperValueOfRightOperand() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Expression result = objectMapper.readValue(jsonExpression.toString(), Expression.class);
		assertEquals(value, result.getRightOperand().getValue());
	}

	@Test
	public void anExpression_WhenBuilt_ShouldHaveProperComparator() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Expression result = objectMapper.readValue(jsonExpression.toString(), Expression.class);
		assertEquals(new Comparator(comparator), result.getComparator());
	}
}

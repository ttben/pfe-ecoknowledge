package business;

import business.improve.ImproveChallenge;
import business.overall.expression.Expression;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class ExpressionTest {

	@Test
	public void anExpression_ShouldBuildProperlyWithNoSymbolicName() {
		JsonObject jsonExpression = new JsonObject();

		JsonObject jsonLeftValueOperand = new JsonObject();
		jsonLeftValueOperand.addProperty("value", 25);
		jsonLeftValueOperand.addProperty("type", "Value");

		JsonObject jsonRightValueOperand = new JsonObject();
		jsonRightValueOperand.addProperty("value", 20);
		jsonRightValueOperand.addProperty("type", "Value");

		jsonExpression.add("leftOperand", jsonLeftValueOperand);
		jsonExpression.add("rightOperand", jsonRightValueOperand);
		jsonExpression.addProperty("comparator", ">");

		ObjectMapper m = new ObjectMapper();
		Object result = null;

		try {
			result = m.readValue(jsonExpression.toString(), Expression.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertNotNull(result);

	}
}

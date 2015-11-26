package fr.unice.polytech.ecoknowledge.language;

import fr.unice.polytech.ecoknowledge.business.conditions.Condition;
import fr.unice.polytech.ecoknowledge.business.conditions.basic.StandardCondition;
import fr.unice.polytech.ecoknowledge.business.conditions.basic.expression.Comparator;
import fr.unice.polytech.ecoknowledge.business.conditions.basic.expression.Expression;
import fr.unice.polytech.ecoknowledge.business.conditions.basic.expression.SymbolicName;
import fr.unice.polytech.ecoknowledge.business.conditions.basic.expression.Value;
import fr.unice.polytech.ecoknowledge.evaluation.Calculator;
import org.junit.Test;

/**
 * Created by Benjamin on 26/11/2015.
 */
public class CalculatorTest {
	@Test
	public void test() {
		Calculator calculator = new Calculator();

		StandardCondition standardCondition  = new StandardCondition(
				new Expression(
						new SymbolicName("TMP_CLI"), new Comparator(">"), new Value(12.0)
				),
				null,
				null
		);

		calculator.evaluateCondition(standardCondition);
	}
}
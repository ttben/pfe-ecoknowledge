package business.overall.expression;

public class Value implements Operand {
	@Override
	public boolean isRequired() {
		return false;
	}
}
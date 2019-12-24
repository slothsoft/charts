package de.slothsoft.charts.swt;

import org.eclipse.swt.widgets.Display;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class SwtRule implements TestRule {

	static final boolean FORBIDDEN;

	static {
		Exception thrownException;
		try {
			Display.getDefault();
			thrownException = null;
		} catch (final Exception e) {
			thrownException = e;
		}
		FORBIDDEN = thrownException != null;
	}

	@Override
	public Statement apply(Statement statement, Description description) {
		return new SwtStatement(statement);
	}

	private static class SwtStatement extends Statement {

		private final Statement statement;

		public SwtStatement(Statement aStatement) {
			this.statement = aStatement;
		}

		@Override
		public void evaluate() throws Throwable {
			if (!FORBIDDEN) {
				this.statement.evaluate();
			}
		}
	}
}
package de.slothsoft.charts.test;

import org.eclipse.swt.widgets.Display;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class SwtRule implements TestRule {

	static final boolean FORBIDDEN;

	static {
		Error thrownError;
		try {
			Display.getDefault();
			thrownError = null;
		} catch (final Error e) {
			thrownError = e;
		}
		FORBIDDEN = thrownError != null;
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
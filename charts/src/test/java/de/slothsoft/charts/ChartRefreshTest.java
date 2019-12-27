package de.slothsoft.charts;

import java.util.Collection;
import java.util.function.Consumer;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ChartRefreshTest extends AbstractChartRefreshTest<Chart> {

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return AbstractChartRefreshTest.createData();
	}

	public ChartRefreshTest(String displayName, Consumer<Chart> methodCall, boolean secondCallChangesToo) {
		super(displayName, methodCall, secondCallChangesToo);
	}

	@Override
	protected Chart createChart() {
		return new TestChart();
	}

}

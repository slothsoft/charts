package de.slothsoft.charts;

import java.util.Collection;
import java.util.function.Consumer;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ChartRefreshTest extends AbstractChartRefreshTest<Chart> {

	@Parameters
	public static Collection<Object[]> data() {
		return AbstractChartRefreshTest.createData();
	}

	public ChartRefreshTest(Consumer<Chart> methodCall, boolean doNotCallTwice) {
		super(methodCall, doNotCallTwice);
	}

	@Override
	protected Chart createChart() {
		return new TestChart();
	}

}

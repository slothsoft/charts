package de.slothsoft.charts;

public class ChartTest extends AbstractChartTest<Chart> {

	@Override
	protected Chart createChart() {
		return new TestChart();
	}
}

package generator;

import java.util.function.Supplier;

import de.slothsoft.charts.Chart;
import de.slothsoft.charts.linechart.LineChart;
import linechart.LineChartExample;

class ChartGenerator {

	public static final ChartGenerator[] ALL = {

			new ChartGenerator("LineChart", LineChart.class, LineChartExample::createChart),

	};

	String displayName;
	Class<?> chartClass;
	Supplier<Chart> chartGenerator;

	public ChartGenerator(String displayName, Class<?> chartClass, Supplier<Chart> chartGenerator) {
		this.displayName = displayName;
		this.chartClass = chartClass;
		this.chartGenerator = chartGenerator;
	}
}

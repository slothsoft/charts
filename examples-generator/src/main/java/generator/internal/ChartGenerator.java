package generator.internal;

import java.util.function.Supplier;

import de.slothsoft.charts.Chart;
import de.slothsoft.charts.linechart.LineChart;
import de.slothsoft.charts.piechart.PieChart;
import linechart.LineChartExample;
import piechart.PieChartExample;

public class ChartGenerator {

	public static final ChartGenerator[] ALL = {

			new ChartGenerator("LineChart", LineChart.class, LineChartExample::createChart),

			new ChartGenerator("PieChart", PieChart.class, PieChartExample::createChart),

	};

	public final String displayName;
	public final Class<?> chartClass;
	public final Supplier<Chart> chartGenerator;

	public ChartGenerator(String displayName, Class<?> chartClass, Supplier<Chart> chartGenerator) {
		this.displayName = displayName;
		this.chartClass = chartClass;
		this.chartGenerator = chartGenerator;
	}
}

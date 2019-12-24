package de.slothsoft.charts.linechart;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.slothsoft.charts.AbstractChartRefreshTest;
import de.slothsoft.charts.Area;

@RunWith(Parameterized.class)
public class LineChartRefreshTest extends AbstractChartRefreshTest<LineChart> {

	@Parameters
	public static Collection<Object[]> data() {
		final List<Object[]> result = AbstractChartRefreshTest.createData();
		result.addAll(Arrays.asList(

				data(chart -> chart.addLine(new DataPointLine(2, 3, 4)), true),

				data(chart -> chart.addLines(new DataPointLine(2, 3, 4), new DataPointLine(1, 2, 3)), true),

				data(chart -> chart.removeLine(chart.lines.get(0)), true),

				data(chart -> chart.removeLines(chart.lines.get(0)), true),

				data(chart -> chart.setDisplayedArea(new Area(1, 2, 3, 4))),

				data(chart -> chart.moveDisplayedArea(1, 2), true))

		);
		return result;
	}

	private static Object[] data(Consumer<LineChart> methodCall) {
		return data(methodCall, false);
	}

	private static Object[] data(Consumer<LineChart> methodCall, boolean secondCallChangesToo) {
		return new Object[]{methodCall, Boolean.valueOf(secondCallChangesToo)};
	}

	public LineChartRefreshTest(Consumer<LineChart> methodCall, boolean secondCallChangesToo) {
		super(methodCall, secondCallChangesToo);
	}

	@Override
	protected LineChart createChart() {
		final LineChart result = new LineChart();
		result.addLine(new DataPointLine(3, 4, 5));
		result.addLine(new DataPointLine(6, 7, 8));
		return result;
	}
}

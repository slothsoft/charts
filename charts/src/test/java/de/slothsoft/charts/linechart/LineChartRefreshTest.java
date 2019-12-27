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
import de.slothsoft.charts.PaintInstructions;
import de.slothsoft.charts.internal.LogGraphicContext;

@RunWith(Parameterized.class)
public class LineChartRefreshTest extends AbstractChartRefreshTest<LineChart> {

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		final List<Object[]> result = AbstractChartRefreshTest.createData();
		result.addAll(Arrays.asList(

				data("addLine", chart -> chart.addLine(new DataPointLine(2, 3, 4)), true),

				data("addLines", chart -> chart.addLines(new DataPointLine(2, 3, 4), new DataPointLine(1, 2, 3)), true),

				data("removeLine", chart -> chart.removeLine(chart.lines.get(0)), true),

				data("removeLines", chart -> chart.removeLines(chart.lines.get(0)), true),

				data("displayedArea", chart -> chart.displayedArea(new Area(1, 2, 3, 4))),

				data("setDisplayedArea", chart -> chart.setDisplayedArea(new Area(1, 2, 3, 4))),

				data("moveDisplayedAreaDirectly", chart -> chart.moveDisplayedAreaDirectly(1, 2), true),

				data("moveDisplayedAreaByChartCoordinates", chart -> chart.moveDisplayedAreaByChartCoordinates(1, 2),
						true),

				data("zoomDisplayedAreaInByChartCoordinates",
						chart -> chart.zoomDisplayedAreaInByChartCoordinates(1, 2), true),

				data("zoomDisplayedAreaInByGraphCoordinates",
						chart -> chart.zoomDisplayedAreaInByGraphCoordinates(1, 2), true),

				data("zoomDisplayedAreaOutByChartCoordinates",
						chart -> chart.zoomDisplayedAreaOutByChartCoordinates(1, 2), true),

				data("zoomDisplayedAreaOutByGraphCoordinates",
						chart -> chart.zoomDisplayedAreaOutByGraphCoordinates(1, 2), true),

				data("resetDisplayedArea", chart -> chart.resetDisplayedArea())

		));
		return result;
	}

	private static Object[] data(String displayName, Consumer<LineChart> methodCall) {
		return data(displayName, methodCall, false);
	}

	private static Object[] data(String displayName, Consumer<LineChart> methodCall, boolean secondCallChangesToo) {
		return new Object[]{displayName, methodCall, Boolean.valueOf(secondCallChangesToo)};
	}

	public LineChartRefreshTest(String displayName, Consumer<LineChart> methodCall, boolean secondCallChangesToo) {
		super(displayName, methodCall, secondCallChangesToo);
	}

	@Override
	protected LineChart createChart() {
		final LineChart result = new LineChart();
		result.addLine(new DataPointLine(3, 4, 5));
		result.addLine(new DataPointLine(6, 7, 8));
		result.setDisplayedArea(result.calculateDisplayedArea());
		result.paintOn(new LogGraphicContext(), new PaintInstructions(new Area(1, 2, 3, 4)));
		return result;
	}
}

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

				data("Line.setColor", chart -> chart.lines.get(0).setColor(0xFFABCDEF)),

				data("Line.color", chart -> chart.lines.get(0).color(0xFFABCDEF)),

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

				data("resetDisplayedArea", chart -> chart.resetDisplayedArea()),

				data("XAxis.setTickSize", chart -> chart.getXAxis().setTickSize(5)),

				data("XAxis.tickSize", chart -> chart.getXAxis().tickSize(5)),

				data("XAxis.setBigTickSize", chart -> chart.getXAxis().setBigTickSize(5)),

				data("XAxis.bigTickSize", chart -> chart.getXAxis().bigTickSize(5)),

				data("XAxis.setTickSteps", chart -> chart.getXAxis().setTickSteps(5)),

				data("XAxis.tickSteps", chart -> chart.getXAxis().tickSteps(5)),

				data("XAxis.setBigTickSteps", chart -> chart.getXAxis().setBigTickSteps(3)),

				data("XAxis.bigTickSteps", chart -> chart.getXAxis().bigTickSteps(3)),

				data("XAxis.arrowSize", chart -> chart.getXAxis().arrowSize(5)),

				data("XAxis.setArrowSize", chart -> chart.getXAxis().setArrowSize(5)),

				data("XAxis.setPosition", chart -> chart.getXAxis().setPosition(XAxis.Position.TOP)),

				data("XAxis.position", chart -> chart.getXAxis().position(XAxis.Position.BOTTOM)),

				data("YAxis.setTickSize", chart -> chart.getYAxis().setTickSize(5)),

				data("YAxis.tickSize", chart -> chart.getYAxis().tickSize(5)),

				data("YAxis.setBigTickSize", chart -> chart.getYAxis().setBigTickSize(5)),

				data("YAxis.bigTickSize", chart -> chart.getYAxis().bigTickSize(5)),

				data("YAxis.setTickSteps", chart -> chart.getYAxis().setTickSteps(5)),

				data("YAxis.tickSteps", chart -> chart.getYAxis().tickSteps(5)),

				data("YAxis.setBigTickSteps", chart -> chart.getYAxis().setBigTickSteps(3)),

				data("YAxis.bigTickSteps", chart -> chart.getYAxis().bigTickSteps(3)),

				data("YAxis.arrowSize", chart -> chart.getYAxis().arrowSize(5)),

				data("YAxis.setArrowSize", chart -> chart.getYAxis().setArrowSize(5)),

				data("YAxis.setPosition", chart -> chart.getYAxis().setPosition(YAxis.Position.LEFT)),

				data("YAxis.position", chart -> chart.getYAxis().position(YAxis.Position.RIGHT))

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

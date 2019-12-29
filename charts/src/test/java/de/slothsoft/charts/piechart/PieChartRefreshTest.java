package de.slothsoft.charts.piechart;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.slothsoft.charts.AbstractChartRefreshTest;

@RunWith(Parameterized.class)
public class PieChartRefreshTest extends AbstractChartRefreshTest<PieChart> {

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		final List<Object[]> result = AbstractChartRefreshTest.createData();
		result.addAll(Arrays.asList(

				data("addSlice", chart -> chart.addSlice(1), true),

				data("addSlices", chart -> chart.addSlices(2), true),

				data("removeSlice", chart -> chart.removeSlice(chart.slices.get(0)), true),

				data("removeSlices", chart -> chart.removeSlices(chart.slices.get(0)), true),

				data("Slice.setColor", chart -> chart.slices.get(0).setColor(0xFFABCDEF)),

				data("Slice.color", chart -> chart.slices.get(0).color(0xFFABCDEF)),

				data("Slice.setValue", chart -> chart.slices.get(0).setValue(4)),

				data("Slice.value", chart -> chart.slices.get(0).value(4)),

				data("setPieColor", chart -> chart.setPieColor(0xFFABCDEF)),

				data("pieColor", chart -> chart.pieColor(0xFFABCDEF)),

				data("setStartAngle", chart -> chart.setStartAngle(150)),

				data("startAngle", chart -> chart.startAngle(100)),

				data("setPieBorder", chart -> chart.setPieBorder(1)),

				data("pieBorder", chart -> chart.pieBorder(5))

		));
		return result;
	}

	private static Object[] data(String displayName, Consumer<PieChart> methodCall) {
		return data(displayName, methodCall, false);
	}

	private static Object[] data(String displayName, Consumer<PieChart> methodCall, boolean secondCallChangesToo) {
		return new Object[]{displayName, methodCall, Boolean.valueOf(secondCallChangesToo)};
	}

	public PieChartRefreshTest(String displayName, Consumer<PieChart> methodCall, boolean secondCallChangesToo) {
		super(displayName, methodCall, secondCallChangesToo);
	}

	@Override
	protected PieChart createChart() {
		final PieChart result = new PieChart();
		result.addSlices(1, 2);
		return result;
	}
}

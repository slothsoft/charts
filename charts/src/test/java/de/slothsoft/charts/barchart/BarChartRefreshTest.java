package de.slothsoft.charts.barchart;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.slothsoft.charts.AbstractChartRefreshTest;

@RunWith(Parameterized.class)
public class BarChartRefreshTest extends AbstractChartRefreshTest<BarChart> {

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		final List<Object[]> result = AbstractChartRefreshTest.createData();
		result.addAll(Arrays.asList(

				data("addBar", chart -> chart.addBar(1), true),

				data("addBars", chart -> chart.addBars(2), true),

				data("removeBar", chart -> chart.removeBar(chart.bars.get(0)), true),

				data("removeBars", chart -> chart.removeBars(chart.bars.get(0)), true),

				data("Bar.setColor", chart -> chart.bars.get(0).setColor(0xFFABCDEF)),

				data("Bar.color", chart -> chart.bars.get(0).color(0xFFABCDEF)),

				data("Bar.setValue", chart -> chart.bars.get(0).setValue(4)),

				data("Bar.value", chart -> chart.bars.get(0).value(4)),

				data("setDefaultBarColor", chart -> chart.setDefaultBarColor(0xFFABCDEF)),

				data("defaultBarColor", chart -> chart.defaultBarColor(0xFFABCDEF))

		));
		return result;
	}

	private static Object[] data(String displayName, Consumer<BarChart> methodCall) {
		return data(displayName, methodCall, false);
	}

	private static Object[] data(String displayName, Consumer<BarChart> methodCall, boolean secondCallChangesToo) {
		return new Object[]{displayName, methodCall, Boolean.valueOf(secondCallChangesToo)};
	}

	public BarChartRefreshTest(String displayName, Consumer<BarChart> methodCall, boolean secondCallChangesToo) {
		super(displayName, methodCall, secondCallChangesToo);
	}

	@Override
	protected BarChart createChart() {
		final BarChart result = new BarChart();
		result.addBars(1, 2);
		return result;
	}
}

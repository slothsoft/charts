package de.slothsoft.charts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the functionality of the {@link RefreshListener}s of {@link Chart} for
 * each implementation of it.
 *
 * @author Stef Schulz
 * @since 0.1.0
 * @param <C> - type of chart
 */

public abstract class AbstractChartRefreshTest<C extends Chart> {

	public static List<Object[]> createData() {
		return new ArrayList<>(Arrays.asList(

				data(chart -> chart.setBackgroundColor(0xFF00FF00)),

				data(chart -> chart.getBorder().setSpace(1)),

				data(chart -> chart.getBorder().setSpaceOnTop(1)),

				data(chart -> chart.getBorder().setSpaceOnRight(1)),

				data(chart -> chart.getBorder().setSpaceOnBottom(1)),

				data(chart -> chart.getBorder().setSpaceOnLeft(1))

		));
	}

	private static Object[] data(Consumer<Chart> methodCall) {
		return data(methodCall, false);
	}

	private static Object[] data(Consumer<Chart> methodCall, boolean doNotCallTwice) {
		return new Object[]{methodCall, Boolean.valueOf(doNotCallTwice)};
	}

	private final Consumer<C> methodCall;
	private final boolean doNotCallTwice;

	protected C chart;

	public AbstractChartRefreshTest(Consumer<C> methodCall, boolean doNotCallTwice) {
		this.methodCall = methodCall;
		this.doNotCallTwice = doNotCallTwice;
	}

	@Before
	public void setUp() {
		this.chart = createChart();
	}

	protected abstract C createChart();

	@Test
	public void testRefreshWasExecuted() throws Exception {
		final RefreshListener.Event[] called = {null};
		this.chart.addRefreshListener(e -> called[0] = e);

		this.methodCall.accept(this.chart);
		Assert.assertNotNull(called[0]);
	}

	@Test
	public void testRefreshWasNotExecutedOnMultipleCalls() throws Exception {
		if (this.doNotCallTwice) return;

		this.methodCall.accept(this.chart);

		final RefreshListener.Event[] called = {null};
		this.chart.addRefreshListener(e -> called[0] = e);

		this.methodCall.accept(this.chart);
		Assert.assertNull(called[0]);
	}
}

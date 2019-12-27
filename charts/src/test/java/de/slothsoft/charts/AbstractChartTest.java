package de.slothsoft.charts;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the functionality of {@link Chart} for each implementation of it.
 *
 * @author Stef Schulz
 * @since 0.1.0
 * @param <C> - type of chart
 */

public abstract class AbstractChartTest<C extends Chart> {

	protected C chart;

	@Before
	public void setUp() {
		this.chart = createChart();
	}

	protected abstract C createChart();

	@Test
	public void testAddRefreshListener() throws Exception {
		final RefreshListener.Event[] called = {null};
		this.chart.addRefreshListener(e -> called[0] = e);

		this.chart.setBackgroundColor(0xABCDEF);
		Assert.assertNotNull(called[0]);
	}

	@Test
	public void testRemoveRefreshListener() throws Exception {
		final RefreshListener.Event[] called = {null};
		final RefreshListener listener = e -> called[0] = e;
		this.chart.addRefreshListener(listener);
		this.chart.removeRefreshListener(listener);

		this.chart.setBackgroundColor(0xABCDEF);
		Assert.assertNull(called[0]);
	}

	@Test
	public void testRemoveRefreshListenerDuringEvent() throws Exception {
		final RefreshListener listener = new RefreshListener() {

			@Override
			public void refreshNeeded(RefreshListener.Event event) {
				AbstractChartTest.this.chart.removeRefreshListener(this);
			}
		};
		this.chart.addRefreshListener(listener);

		this.chart.setBackgroundColor(0xABCDEF);
		Assert.assertNotNull("throws an exception if implemented incorrectly", this.chart.refreshListeners);
	}

	@Test
	public void testSetBackgroundColor() throws Exception {
		this.chart.setBackgroundColor(0xFF00FF00);
		Assert.assertEquals(0xFF00FF00, this.chart.getBackgroundColor());

		this.chart.backgroundColor(0xFFFF0000);
		Assert.assertEquals(0xFFFF0000, this.chart.getBackgroundColor());
	}
}

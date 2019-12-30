package de.slothsoft.charts.swing;

import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.slothsoft.charts.linechart.LineChart;

/**
 * Tests that for all kinds of changes to the model or control the GUI is redrawn.
 * Something like this is needed for every control.
 *
 * @author Stef Schulz
 * @since 0.2.0
 */

public class ChartControlRefreshTest {

	private ChartControl control;
	Consumer<Void> paintListener;

	@Before
	public void setUp() {
		this.control = new ChartControl() {
			private static final long serialVersionUID = 3312696566403152084L;

			@Override
			public void repaint() {
				super.repaint();

				if (ChartControlRefreshTest.this.paintListener != null) {
					ChartControlRefreshTest.this.paintListener.accept(null);
				}
			}
		};
	}

	@Test
	public void testRefreshForSetModel() throws Exception {
		Assert.assertNull(this.control.getModel());

		final boolean[] called = {false};
		this.paintListener = e -> called[0] = true;
		this.control.setModel(new LineChart());

		Assert.assertTrue(called[0]);
	}

	@Test
	public void testRefreshForSetModelNull() throws Exception {
		this.control.setModel(new LineChart());
		Assert.assertNotNull(this.control.getModel());

		final boolean[] called = {false};
		this.paintListener = e -> called[0] = true;
		this.control.setModel(null);

		Assert.assertTrue(called[0]);
	}

	@Test
	public void testRefreshForModel() throws Exception {
		Assert.assertNull(this.control.getModel());

		final boolean[] called = {false};
		this.paintListener = e -> called[0] = true;
		this.control.model(new LineChart());

		Assert.assertTrue(called[0]);
	}

	@Test
	public void testRefreshForModelNull() throws Exception {
		this.control.setModel(new LineChart());
		Assert.assertNotNull(this.control.getModel());

		final boolean[] called = {false};
		this.paintListener = e -> called[0] = true;
		this.control.model(null);

		Assert.assertTrue(called[0]);
	}

	@Test
	public void testRefreshForChangeToModel() throws Exception {
		final LineChart chart = new LineChart();
		this.control.setModel(chart);

		final boolean[] called = {false};
		this.paintListener = e -> called[0] = true;
		chart.getBorder().setSpace(0);

		Assert.assertTrue(called[0]);
	}

	@Test
	public void testRefreshForChangeToNewModel() throws Exception {
		final LineChart oldChart = new LineChart();
		this.control.setModel(oldChart);
		final LineChart newChart = new LineChart();
		this.control.setModel(newChart);

		final boolean[] called = {false};
		this.paintListener = e -> called[0] = true;
		newChart.getBorder().setSpace(0);

		Assert.assertTrue(called[0]);
	}

	@Test
	public void testNoRefreshForChangeToOldModel() throws Exception {
		final LineChart oldChart = new LineChart();
		this.control.setModel(oldChart);
		final LineChart newChart = new LineChart();
		this.control.setModel(newChart);

		final boolean[] called = {false};
		this.paintListener = e -> called[0] = true;
		oldChart.getBorder().setSpace(0);

		Assert.assertFalse(called[0]);
	}
}
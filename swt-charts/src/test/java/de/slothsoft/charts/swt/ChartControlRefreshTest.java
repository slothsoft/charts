package de.slothsoft.charts.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.slothsoft.charts.linechart.LineChart;

/**
 * Tests that for all kinds of changes to the model or control the GUI is redrawn.
 * Something like this is needed for every control.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class ChartControlRefreshTest {

	@Rule
	public SwtRule swtRule = new SwtRule();

	private Shell shell;
	private ChartControl control;

	@Before
	public void setUp() {
		this.shell = new Shell();
		this.shell.setLayout(new FillLayout());
		this.control = new ChartControl(this.shell, SWT.NONE);
	}

	@After
	public void tearDown() {
		this.shell.dispose();
	}

	@Test
	public void testNoRefreshForUpdate() throws Exception {
		this.shell.open();
		Assert.assertNull(this.control.getModel());

		final boolean[] called = {false};
		this.control.addPaintListener(e -> called[0] = true);
		this.control.update();

		Assert.assertFalse(called[0]);
	}

	@Test
	public void testRefreshForSetModel() throws Exception {
		this.shell.open();
		Assert.assertNull(this.control.getModel());

		final boolean[] called = {false};
		this.control.addPaintListener(e -> called[0] = true);
		this.control.setModel(new LineChart());
		this.control.update();

		Assert.assertTrue(called[0]);
	}

	@Test
	public void testRefreshForSetModelNull() throws Exception {
		this.shell.open();
		this.control.setModel(new LineChart());
		Assert.assertNotNull(this.control.getModel());

		final boolean[] called = {false};
		this.control.addPaintListener(e -> called[0] = true);
		this.control.setModel(null);
		this.control.update();

		Assert.assertTrue(called[0]);
	}

	@Test
	public void testRefreshForModel() throws Exception {
		this.shell.open();
		Assert.assertNull(this.control.getModel());

		final boolean[] called = {false};
		this.control.addPaintListener(e -> called[0] = true);
		this.control.model(new LineChart());
		this.control.update();

		Assert.assertTrue(called[0]);
	}

	@Test
	public void testRefreshForModelNull() throws Exception {
		this.shell.open();
		this.control.setModel(new LineChart());
		Assert.assertNotNull(this.control.getModel());

		final boolean[] called = {false};
		this.control.addPaintListener(e -> called[0] = true);
		this.control.model(null);
		this.control.update();

		Assert.assertTrue(called[0]);
	}

	@Test
	public void testRefreshForChangeToModel() throws Exception {
		this.shell.open();

		final LineChart chart = new LineChart();
		this.control.setModel(chart);
		this.control.update();

		final boolean[] called = {false};
		this.control.addPaintListener(e -> called[0] = true);
		chart.getBorder().setSpace(0);
		this.control.update();

		Assert.assertTrue(called[0]);
	}

	@Test
	public void testRefreshForChangeToNewModel() throws Exception {
		this.shell.open();

		final LineChart oldChart = new LineChart();
		this.control.setModel(oldChart);
		final LineChart newChart = new LineChart();
		this.control.setModel(newChart);
		this.control.update();

		final boolean[] called = {false};
		this.control.addPaintListener(e -> called[0] = true);
		newChart.getBorder().setSpace(0);
		this.control.update();

		Assert.assertTrue(called[0]);
	}

	@Test
	public void testNoRefreshForChangeToOldModel() throws Exception {
		this.shell.open();

		final LineChart oldChart = new LineChart();
		this.control.setModel(oldChart);
		final LineChart newChart = new LineChart();
		this.control.setModel(newChart);
		this.control.update();

		final boolean[] called = {false};
		this.control.addPaintListener(e -> called[0] = true);
		oldChart.getBorder().setSpace(0);
		this.control.update();

		Assert.assertFalse(called[0]);
	}

	@Test
	public void testNoRefreshAfterDisposal() throws Exception {
		this.shell.open();

		final LineChart chart = new LineChart();
		this.control.setModel(chart);
		this.control.update();

		final boolean[] called = {false};
		this.control.addPaintListener(e -> called[0] = true);
		this.control.dispose();
		chart.getBorder().setSpace(0);

		Assert.assertFalse(called[0]);
	}
}
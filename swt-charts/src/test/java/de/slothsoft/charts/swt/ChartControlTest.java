package de.slothsoft.charts.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.slothsoft.charts.Chart;

public class ChartControlTest {

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
	public void testRedrawWithoutChart() throws Exception {
		this.shell.open();
		Assert.assertNull(this.control.getModel());

		final boolean[] called = {false};
		this.control.addPaintListener(e -> called[0] = true);
		this.control.redraw();
		this.control.update();

		Assert.assertTrue(called[0]);
	}

	@Test
	public void testSetModel() throws Exception {
		final boolean[] called = {false};
		final Chart chart = new TestChart().paintFunction((gc, i) -> called[0] = true);
		this.control.setModel(chart);
		this.shell.open();

		Assert.assertSame(chart, this.control.getModel());
		Assert.assertTrue(called[0]);
	}

	@Test
	public void testSetModelLater() throws Exception {
		this.shell.open();

		final boolean[] called = {false};
		final Chart chart = new TestChart().paintFunction((gc, i) -> called[0] = true);
		this.control.setModel(chart);
		this.control.update();

		Assert.assertSame(chart, this.control.getModel());
		Assert.assertTrue(called[0]);
	}

	@Test
	public void testModel() throws Exception {
		final boolean[] called = {false};
		final Chart chart = new TestChart().paintFunction((gc, i) -> called[0] = true);
		this.control.model(chart);
		this.shell.open();

		Assert.assertSame(chart, this.control.getModel());
		Assert.assertTrue(called[0]);
	}

	@Test
	public void testModelLater() throws Exception {
		this.shell.open();

		final boolean[] called = {false};
		final Chart chart = new TestChart().paintFunction((gc, i) -> called[0] = true);
		this.control.model(chart);
		this.control.update();

		Assert.assertSame(chart, this.control.getModel());
		Assert.assertTrue(called[0]);
	}

	@Test
	public void testHookToPaintListeners() throws Exception {
		final TestChart chart = new TestChart();
		this.control.model(chart);
		this.shell.open();

		final boolean[] called = {false};
		this.control.addPaintListener(e -> called[0] = true);
		chart.fireRefreshNeeded();
		this.control.update();

		Assert.assertTrue(called[0]);
	}

	@Test
	public void testHookToPaintListenersRemoveAfterShellDispose() throws Exception {
		final TestChart chart = new TestChart();
		this.control.setModel(chart);
		this.shell.open();

		final boolean[] called = {false};
		this.control.addPaintListener(e -> called[0] = true);

		this.shell.dispose();

		chart.fireRefreshNeeded();

		Assert.assertFalse(called[0]);
	}

	@Test
	public void testHookToPaintListenersRemoveAfterDispose() throws Exception {
		final TestChart chart = new TestChart();
		this.control.setModel(chart);
		this.shell.open();

		final boolean[] called = {false};
		this.control.addPaintListener(e -> called[0] = true);

		this.control.dispose();

		chart.fireRefreshNeeded();

		Assert.assertFalse(called[0]);
	}

}
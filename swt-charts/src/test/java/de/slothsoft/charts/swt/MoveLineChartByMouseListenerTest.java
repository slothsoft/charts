package de.slothsoft.charts.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.slothsoft.charts.linechart.DataPointLine;
import de.slothsoft.charts.linechart.LineChart;

/**
 * We assume the functions {@link MoveLineChartByMouseListener} uses are tested well in
 * the {@link LineChart}, so we only simulate the clicks and try to figure out if the
 * right ones are used.
 */

public class MoveLineChartByMouseListenerTest {

	@Rule
	public SwtRule swtRule = new SwtRule();

	private Shell shell;
	private ChartControl control;

	private LineChart lineChart;
	private LineChart twinChart;
	private MoveLineChartByMouseListener listener;

	@Before
	public void setUp() {
		this.lineChart = createLineChart();
		this.twinChart = createLineChart();

		this.shell = new Shell();
		this.shell.setLayout(new FillLayout());
		this.shell.setSize(800, 600);

		this.control = new ChartControl(this.shell, SWT.NONE).model(this.lineChart);
		this.shell.open();

		this.listener = new MoveLineChartByMouseListener(this.lineChart);
		paintLineChart(this.twinChart); // we need to paint this one too
	}

	private static LineChart createLineChart() {
		final LineChart result = new LineChart();
		result.addLine(new DataPointLine(-3, -1, 0, 2.5, 2.95));
		return result;
	}

	private void paintLineChart(LineChart chart) {
		final Rectangle rect = this.control.getBounds();
		SwtChartUtil.createImage(chart, rect.width, rect.height).dispose();
	}

	@After
	public void tearDown() {
		this.shell.dispose();
	}

	@Test
	public void testMove() throws Exception {
		MouseEvent event = createEvent(10, 20);
		event.button = 1;
		this.listener.mouseDown(event);
		Assert.assertTrue(this.listener.mouseDown);

		Assert.assertEquals(this.twinChart.getDisplayedArea(), this.lineChart.getDisplayedArea());

		event = createEvent(15, 13);
		this.listener.mouseMove(event);

		this.twinChart.moveDisplayedAreaByChartCoordinates(-5, -7);
		Assert.assertEquals(this.twinChart.getDisplayedArea(), this.lineChart.getDisplayedArea());
	}

	private MouseEvent createEvent(int x, int y) {
		final Event event = new Event();
		event.widget = this.control;
		event.time = (int) System.currentTimeMillis();
		event.x = x;
		event.y = y;
		return new MouseEvent(event);
	}

	@Test
	public void testMoveOutsideGraphArea() throws Exception {
		Assert.assertEquals(this.twinChart.getDisplayedArea(), this.lineChart.getDisplayedArea());

		final MouseEvent event = createEvent(-15, -13);
		this.listener.mouseMove(event);

		Assert.assertEquals(this.twinChart.getDisplayedArea(), this.lineChart.getDisplayedArea());
	}

	@Test
	public void testMoveAfterMouseUp() throws Exception {
		MouseEvent event = createEvent(10, 20);
		event.button = 1;
		this.listener.mouseDown(event);
		Assert.assertTrue(this.listener.mouseDown);
		this.listener.mouseUp(event);
		Assert.assertFalse(this.listener.mouseDown);

		Assert.assertEquals(this.twinChart.getDisplayedArea(), this.lineChart.getDisplayedArea());

		event = createEvent(15, 13);
		this.listener.mouseMove(event);

		Assert.assertEquals(this.twinChart.getDisplayedArea(), this.lineChart.getDisplayedArea());
	}

	@Test
	public void testNoMoveOutsideGraphArea() throws Exception {
		final MouseEvent event = createEvent(-10, -20);
		event.button = 1;
		this.listener.mouseDown(event);
		Assert.assertFalse(this.listener.mouseDown);
	}

	@Test
	public void testMouseMoveCursorInGraphArea() throws Exception {
		this.listener.mouseMove(createEvent(10, 20));

		Assert.assertEquals(MoveLineChartByMouseListener.getHandCursor(this.shell.getDisplay()),
				this.control.getCursor());
	}

	@Test
	public void testMouseMoveCursorOutsideGraphArea() throws Exception {
		this.listener.mouseMove(createEvent(-10, -20));

		Assert.assertEquals(null, this.control.getCursor());
	}

	@Test
	public void testZoomIn() throws Exception {
		final MouseEvent event = createEvent(10, 20);
		event.count = 3;
		this.listener.mouseScrolled(event);

		this.twinChart.zoomDisplayedAreaInByChartCoordinates(10, 20);
		Assert.assertEquals(this.twinChart.getDisplayedArea(), this.lineChart.getDisplayedArea());
	}

	@Test
	public void testZoomOut() throws Exception {
		final MouseEvent event = createEvent(20, 30);
		event.count = -3;
		this.listener.mouseScrolled(event);

		this.twinChart.zoomDisplayedAreaOutByChartCoordinates(20, 30);
		Assert.assertEquals(this.twinChart.getDisplayedArea(), this.lineChart.getDisplayedArea());
	}

	@Test
	public void testSetPosition() throws Exception {
		this.listener.setMovementMouseButton(2);
		Assert.assertEquals(2, this.listener.getMovementMouseButton());

		this.listener.movementMouseButton(3);
		Assert.assertEquals(3, this.listener.getMovementMouseButton());
	}
}

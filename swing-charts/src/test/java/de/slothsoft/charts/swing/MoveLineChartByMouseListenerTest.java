package de.slothsoft.charts.swing;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JLabel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.slothsoft.charts.linechart.DataPointLine;
import de.slothsoft.charts.linechart.LineChart;

/**
 * We assume the functions {@link MoveLineChartByMouseListener} uses are tested well in
 * the {@link LineChart}, so we only simulate the clicks and try to figure out if the
 * right ones are used.
 */

public class MoveLineChartByMouseListenerTest {

	private ChartControl control;

	private LineChart lineChart;
	private LineChart twinChart;
	private MoveLineChartByMouseListener listener;

	@Before
	public void setUp() {
		this.lineChart = createLineChart();
		this.twinChart = createLineChart();

		this.control = new ChartControl(this.lineChart);
		this.control.setSize(400, 300);

		this.listener = new MoveLineChartByMouseListener(this.lineChart);
		paintLineChart(this.lineChart); // we need to paint this
		paintLineChart(this.twinChart); // we need to paint this one too
	}

	private static LineChart createLineChart() {
		final LineChart result = new LineChart();
		result.addLine(new DataPointLine(-3, -1, 0, 2.5, 2.95));
		return result;
	}

	private void paintLineChart(LineChart chart) {
		final Rectangle rect = this.control.getBounds();
		SwingChartUtil.createImage(chart, rect.width, rect.height);
	}

	@Test
	public void testMove() throws Exception {
		MouseEvent event = createEvent(10, 20).button(1).build();
		this.listener.mousePressed(event);
		Assert.assertTrue(this.listener.mouseDown);

		Assert.assertEquals(this.twinChart.getDisplayedArea(), this.lineChart.getDisplayedArea());

		event = createEvent(15, 13).build();
		this.listener.mouseDragged(event);

		this.twinChart.moveDisplayedAreaByChartCoordinates(-5, -7);
		Assert.assertEquals(this.twinChart.getDisplayedArea(), this.lineChart.getDisplayedArea());
	}

	private MouseEventBuilder createEvent(int x, int y) {
		return new MouseEventBuilder(this.control, x, y);
	}

	@Test
	public void testMoveOutsideGraphArea() throws Exception {
		Assert.assertEquals(this.twinChart.getDisplayedArea(), this.lineChart.getDisplayedArea());

		final MouseEvent event = createEvent(-15, -13).build();
		this.listener.mouseDragged(event);

		Assert.assertEquals(this.twinChart.getDisplayedArea(), this.lineChart.getDisplayedArea());
	}

	@Test
	public void testMoveAfterMouseUp() throws Exception {
		MouseEvent event = createEvent(10, 20).button(1).build();
		this.listener.mousePressed(event);
		Assert.assertTrue(this.listener.mouseDown);
		this.listener.mouseReleased(event);
		Assert.assertFalse(this.listener.mouseDown);

		Assert.assertEquals(this.twinChart.getDisplayedArea(), this.lineChart.getDisplayedArea());

		event = createEvent(15, 13).build();
		this.listener.mouseDragged(event);

		Assert.assertEquals(this.twinChart.getDisplayedArea(), this.lineChart.getDisplayedArea());
	}

	@Test
	public void testNoMoveOutsideGraphArea() throws Exception {
		final MouseEvent event = createEvent(-10, -20).button(1).build();
		this.listener.mousePressed(event);
		Assert.assertFalse(this.listener.mouseDown);
	}

	@Test
	public void testMouseMoveCursorInGraphArea() throws Exception {
		this.listener.mouseMoved(createEvent(10, 20).build());

		Assert.assertSame(MoveLineChartByMouseListener.HAND_CURSOR, this.control.getCursor());
	}

	@Test
	public void testMouseMoveCursorOutsideGraphArea() throws Exception {
		this.listener.mouseMoved(createEvent(-10, -20).build());

		Assert.assertSame(new JLabel().getCursor(), this.control.getCursor());
	}

	@Test
	public void testZoomIn() throws Exception {
		final MouseWheelEvent event = createEvent(10, 20).wheelRotation(-1).buildForWheel();
		this.listener.mouseWheelMoved(event);

		this.twinChart.zoomDisplayedAreaInByChartCoordinates(10, 20);
		Assert.assertEquals(this.twinChart.getDisplayedArea(), this.lineChart.getDisplayedArea());
	}

	@Test
	public void testZoomOut() throws Exception {
		final MouseWheelEvent event = createEvent(20, 30).wheelRotation(1).buildForWheel();
		this.listener.mouseWheelMoved(event);

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

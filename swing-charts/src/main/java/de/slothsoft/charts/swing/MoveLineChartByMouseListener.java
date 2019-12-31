package de.slothsoft.charts.swing;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Objects;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.linechart.LineChart;

/**
 * A {@link MouseListener}, {@link MouseMotionListener} and {@link MouseWheelListener} to
 * move the diagram with the mouse. It's advised to add this listener by using the
 * {@link MoveLineChartByMouseListener#hookToControl(Component, LineChart)} method.
 *
 * @author Stef Schulz
 * @since 0.2.0
 */

public class MoveLineChartByMouseListener implements MouseListener, MouseMotionListener, MouseWheelListener {

	/**
	 * Creates a new {@link MoveLineChartByMouseListener}, hooks it to a control (not
	 * necessary a {@link ChartControl}, but that's probably a good idea) and returns the
	 * brand new instance.
	 *
	 * @param control the control, probably a {@link ChartControl}
	 * @param chart the displayed chart
	 * @return the brand new listener
	 */

	public static MoveLineChartByMouseListener hookToControl(Component control, LineChart chart) {
		final MoveLineChartByMouseListener result = new MoveLineChartByMouseListener(chart);
		control.addMouseListener(result);
		control.addMouseMotionListener(result);
		control.addMouseWheelListener(result);
		return result;
	}

	static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);

	private final LineChart chart;
	boolean mouseDown;
	private int mouseDownX;
	private int mouseDownY;

	private int movementMouseButton = MouseEvent.BUTTON1;

	/**
	 * Constructor.
	 *
	 * @param chart the chart this listener uses
	 */

	public MoveLineChartByMouseListener(LineChart chart) {
		this.chart = Objects.requireNonNull(chart);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (isInGraphArea(e) && e.getButton() == this.movementMouseButton) {
			this.mouseDown = true;
			this.mouseDownX = e.getX();
			this.mouseDownY = e.getY();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == this.movementMouseButton) {
			this.mouseDown = false;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		final Component control = e.getComponent();
		if (isInGraphArea(e)) {
			control.setCursor(HAND_CURSOR);
		} else {
			control.setCursor(null);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (this.mouseDown) {
			final int diffX = this.mouseDownX - e.getX();
			final int diffY = e.getY() - this.mouseDownY; // for line charts y is flipped

			this.chart.moveDisplayedAreaByChartCoordinates(diffX, diffY);

			this.mouseDownX = e.getX();
			this.mouseDownY = e.getY();
		}
	}

	private boolean isInGraphArea(MouseEvent e) {
		final Component control = e.getComponent();
		final Dimension controlSize = control.getSize();
		final Area actualArea = this.chart.calculateGraphArea(controlSize.getWidth(), controlSize.getHeight());
		return actualArea.containsPoint(e.getX(), e.getY());
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (isInGraphArea(e)) {
			if (e.getWheelRotation() < 0) {
				this.chart.zoomDisplayedAreaInByChartCoordinates(e.getX(), e.getY());
			} else {
				this.chart.zoomDisplayedAreaOutByChartCoordinates(e.getX(), e.getY());
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// nothing to do yet
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// nothing to do yet
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// nothing to do yet
	}

	/**
	 * Returns the button that needs to be pressed to move the chart.
	 * <ul>
	 * <li><code>MouseEvent.BUTTON1</code></li>
	 * <li><code>MouseEvent.BUTTON2</code></li>
	 * <li><code>MouseEvent.BUTTON3</code></li>
	 * </ul>
	 *
	 * @return the mouse button
	 */

	public int getMovementMouseButton() {
		return this.movementMouseButton;
	}

	/**
	 * Sets the button that needs to be pressed to move the chart.
	 * <ul>
	 * <li><code>MouseEvent.BUTTON1</code></li>
	 * <li><code>MouseEvent.BUTTON2</code></li>
	 * <li><code>MouseEvent.BUTTON3</code></li>
	 * </ul>
	 *
	 * @param newMovementMouseButton the mouse button
	 * @return this instance
	 */

	public MoveLineChartByMouseListener movementMouseButton(int newMovementMouseButton) {
		setMovementMouseButton(newMovementMouseButton);
		return this;
	}

	/**
	 * Sets the button that needs to be pressed to move the chart.
	 * <ul>
	 * <li><code>MouseEvent.BUTTON1</code></li>
	 * <li><code>MouseEvent.BUTTON2</code></li>
	 * <li><code>MouseEvent.BUTTON3</code></li>
	 * </ul>
	 *
	 * @param movementMouseButton the mouse button
	 */

	public void setMovementMouseButton(int movementMouseButton) {
		this.movementMouseButton = movementMouseButton;
	}

}

package de.slothsoft.charts.swt;

import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.linechart.LineChart;

/**
 * A {@link MouseListener}, {@link MouseMoveListener} and {@link MouseWheelListener} to
 * move the diagram with the mouse. It's advised to add this listener by using the
 * {@link MoveLineChartByMouseListener#hookToControl(Control, LineChart)} method.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class MoveLineChartByMouseListener implements MouseListener, MouseMoveListener, MouseWheelListener {

	/**
	 * Creates a new {@link MoveLineChartByMouseListener}, hooks it to a control (not
	 * necessary a {@link ChartControl}, but that's probably a good idea) and returns the
	 * brand new instance.
	 *
	 * @param control the control, probably a {@link ChartControl}
	 * @param chart the displayed chart
	 * @return the brand new listener
	 */

	public static MoveLineChartByMouseListener hookToControl(Control control, LineChart chart) {
		final MoveLineChartByMouseListener result = new MoveLineChartByMouseListener(chart);
		control.addMouseListener(result);
		control.addMouseMoveListener(result);
		control.addMouseWheelListener(result);
		return result;
	}

	private static Cursor handCursor;

	static Cursor getHandCursor(Display display) {
		if (handCursor == null) {
			handCursor = new Cursor(display, SWT.CURSOR_HAND);
		}
		return handCursor;
	}

	private final LineChart chart;
	boolean mouseDown;
	private int mouseDownX;
	private int mouseDownY;

	private int movementMouseButton = 1;

	/**
	 * Constructor.
	 *
	 * @param chart the chart this listener uses
	 */

	public MoveLineChartByMouseListener(LineChart chart) {
		this.chart = Objects.requireNonNull(chart);
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		// nothing to do yet
	}

	@Override
	public void mouseDown(MouseEvent e) {
		if (isInGraphArea(e) && e.button == this.movementMouseButton) {
			this.mouseDown = true;
			this.mouseDownX = e.x;
			this.mouseDownY = e.y;
		}
	}

	@Override
	public void mouseUp(MouseEvent e) {
		if (e.button == this.movementMouseButton) {
			this.mouseDown = false;
		}
	}

	@Override
	public void mouseMove(MouseEvent e) {
		final Control control = ((Control) e.widget);
		if (isInGraphArea(e)) {
			control.setCursor(getHandCursor(control.getDisplay()));
		} else {
			control.setCursor(null);
		}

		if (this.mouseDown) {
			final int diffX = this.mouseDownX - e.x;
			final int diffY = e.y - this.mouseDownY; // for line charts y is flipped

			this.chart.moveDisplayedAreaByChartCoordinates(diffX, diffY);

			this.mouseDownX = e.x;
			this.mouseDownY = e.y;
		}
	}

	private boolean isInGraphArea(MouseEvent e) {
		final Control control = ((Control) e.widget);
		final Point controlSize = control.getSize();
		final Area actualArea = this.chart.calculateGraphArea(controlSize.x, controlSize.y);
		return actualArea.containsPoint(e.x, e.y);
	}

	@Override
	public void mouseScrolled(MouseEvent e) {
		if (isInGraphArea(e)) {
			if (e.count < 0) {
				this.chart.zoomDisplayedAreaOutByChartCoordinates(e.x, e.y);
			} else {
				this.chart.zoomDisplayedAreaInByChartCoordinates(e.x, e.y);
			}
		}
	}

	/**
	 * Returns the button that needs to be pressed to move the chart.
	 * <ul>
	 * <li>1 for the first button (usually 'left')</li>
	 * <li>2 for the second button (usually 'middle')</li>
	 * <li>3 for the third button (usually 'right')</li>
	 * <li>etc.</li>
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
	 * <li>1 for the first button (usually 'left')</li>
	 * <li>2 for the second button (usually 'middle')</li>
	 * <li>3 for the third button (usually 'right')</li>
	 * <li>etc.</li>
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
	 * <li>1 for the first button (usually 'left')</li>
	 * <li>2 for the second button (usually 'middle')</li>
	 * <li>3 for the third button (usually 'right')</li>
	 * <li>etc.</li>
	 * </ul>
	 *
	 * @param movementMouseButton the mouse button
	 */

	public void setMovementMouseButton(int movementMouseButton) {
		this.movementMouseButton = movementMouseButton;
	}

}

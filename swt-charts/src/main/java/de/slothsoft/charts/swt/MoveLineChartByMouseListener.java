package de.slothsoft.charts.swt;

import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.linechart.LineChart;

/**
 * A {@link MouseListener} and {@link MouseMoveListener} to move the diagram with the
 * mouse. It's advised to add this listener by using the
 * {@link MoveLineChartByMouseListener#hookToControl(Control, LineChart)} method.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class MoveLineChartByMouseListener implements MouseListener, MouseMoveListener {

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
		return result;
	}

	private static Cursor handCursor;

	private final LineChart chart;
	private boolean mouseDown;
	private int mouseDownX;
	private int mouseDownY;

	public MoveLineChartByMouseListener(LineChart chart) {
		this.chart = Objects.requireNonNull(chart);
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		// nothing to do yet
	}

	@Override
	public void mouseDown(MouseEvent e) {
		this.mouseDown = true;
		this.mouseDownX = e.x;
		this.mouseDownY = e.y;
	}

	@Override
	public void mouseUp(MouseEvent e) {
		this.mouseDown = false;
	}

	@Override
	public void mouseMove(MouseEvent e) {
		final Control control = ((Control) e.widget);
		final Point controlSize = control.getSize();
		final Area actualArea = this.chart.calculateGraphArea(controlSize.x, controlSize.y);

		if (actualArea.containsPoint(e.x, e.y)) {
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

	private static Cursor getHandCursor(Display display) {
		if (handCursor == null) {
			handCursor = new Cursor(display, SWT.CURSOR_HAND);
		}
		return handCursor;
	}

}

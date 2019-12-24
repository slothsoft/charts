package de.slothsoft.charts;

import java.util.Arrays;
import java.util.Collection;

import de.slothsoft.charts.common.Border;
import de.slothsoft.charts.internal.RefreshListeners;

/**
 * This is the base class this library is for. It represents an abstract chart of some
 * sort, weather it's a line chart or something entirely different. Classes are connected
 * like this: <br>
 * <img src="https://raw.githubusercontent.com/wiki/slothsoft/charts/images/UML.png" alt=
 * "UML Diagram">
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public abstract class Chart {

	private final Border border = new Border();
	private int backgroundColor = 0xFFFFFFFF;

	RefreshListeners refreshListeners = new RefreshListeners(this);

	/**
	 * Default constructor.
	 */

	public Chart() {
		hookRefreshListenersToChartParts();
	}

	private void hookRefreshListenersToChartParts() {
		fetchChartParts().forEach(part -> part.addRefreshListener(e -> fireRefreshNeeded()));
	}

	/**
	 * Paints the current content onto the graphic context. Checks the instructions for
	 * what to paint. The instructions contain the area in display coordinates, starting
	 * from the top left with 0|0 and ending bottom right with something like 800|600.
	 *
	 * @param gc graphic context; coordinates are relative to the screen
	 * @param instructions additional instructions like the area to paint on
	 */

	public void paintOn(GraphicContext gc, PaintInstructions instructions) {
		gc.setColor(this.backgroundColor);
		gc.fillRectangle(instructions.area);

		// paint all the parts that make up the chart

		Area chartArea = instructions.getArea();
		for (final ChartPart part : fetchChartParts()) {
			part.paintOn(gc, instructions.area(chartArea));
			chartArea = part.snipNecessarySpace(chartArea);
		}

		// now paint the actual graph

		final Area graphArea = chartArea;
		try {
			gc.clip(graphArea);
			gc.translate(graphArea.getStartX(), graphArea.getStartY());

			paintGraph(gc, instructions.area(chartArea.copy().startX(0).startY(0)));
		} finally {
			gc.translate(-graphArea.getStartX(), -graphArea.getEndY());
			gc.clip(null);
		}
	}

	/**
	 * Calculates the graph area by removing the {@link ChartPart}s from the entire width
	 * and height.
	 *
	 * @param width the entire width
	 * @param height the entire height
	 * @return the graph area
	 */

	public Area calculateGraphArea(double width, double height) {
		Area result = new Area(width, height);
		for (final ChartPart part : fetchChartParts()) {
			result = part.snipNecessarySpace(result);
		}
		return result;
	}

	/**
	 * Returns all the {@link ChartPart}s this chart nows about. Override this method to
	 * add custom parts.
	 *
	 * @return a list of chart parts
	 */

	protected Collection<ChartPart> fetchChartParts() {
		return Arrays.asList(this.border);
	}

	/**
	 * Paints the actual graph (the white part in <a href=
	 * "https://github.com/slothsoft/charts/wiki/Preliminary-Considerations">Preliminary
	 * Considerations</a>). Coordinates are starting from the top left with 0|0 and ending
	 * bottom right with something like 800|600.
	 *
	 * @param gc graphic context; coordinates are relative to the screen
	 * @param instructions additional instructions like the area to paint on
	 */

	protected abstract void paintGraph(GraphicContext gc, PaintInstructions instructions);

	/**
	 * Fires a default event for the {@link RefreshListener}s of this chart.
	 */

	protected void fireRefreshNeeded() {
		fireRefreshNeeded(new RefreshListener.Event(this));
	}

	/**
	 * Fires an event for the {@link RefreshListener}s of this chart.
	 *
	 * @param event the event to be fired
	 */

	protected void fireRefreshNeeded(RefreshListener.Event event) {
		this.refreshListeners.fireRefreshNeeded(event);
	}

	/**
	 * Adds a refresh listener that is called whenever this {@link Chart} needs to be
	 * redrawn by the GUI.
	 *
	 * @param listener a listener
	 */

	public void addRefreshListener(RefreshListener listener) {
		this.refreshListeners.addRefreshListener(listener);
	}

	/**
	 * Removes a refresh listener that was called whenever this {@link Chart} needed to be
	 * redrawn by the GUI. Does nothing if the listener was never added.
	 *
	 * @param listener a listener
	 */

	public void removeRefreshListener(RefreshListener listener) {
		this.refreshListeners.removeRefreshListener(listener);
	}

	/**
	 * Returns the border this chart has. The border is supposed to be around everything
	 * else like this:<br>
	 * <img src=
	 * "https://raw.githubusercontent.com/wiki/slothsoft/charts/images/chart-design.png"
	 * alt="Chart Parts">
	 *
	 * @return the border
	 */

	public Border getBorder() {
		return this.border;
	}

	/**
	 * Returns the color as ARGB int, e.g. red is <code>0xFFFF0000</code> and blue is
	 * <code>0xFF0000FF</code>.
	 *
	 * @return the color
	 */

	public int getBackgroundColor() {
		return this.backgroundColor;
	}

	/**
	 * Sets the color as ARGB int, e.g. red is <code>0xFFFF0000</code> and blue is
	 * <code>0xFF0000FF</code>.
	 *
	 * @param newBackgroundColor the color
	 * @return this instance
	 */

	public Chart backgroundColor(int newBackgroundColor) {
		setBackgroundColor(newBackgroundColor);
		return this;
	}

	/**
	 * Sets the color as ARGB int, e.g. red is <code>0xFFFF0000</code> and blue is
	 * <code>0xFF0000FF</code>.
	 *
	 * @param backgroundColor the color
	 */

	public void setBackgroundColor(int backgroundColor) {
		final int oldBackgroundColor = this.backgroundColor;
		this.backgroundColor = backgroundColor;
		if (oldBackgroundColor != this.backgroundColor) {
			fireRefreshNeeded();
		}
	}

}

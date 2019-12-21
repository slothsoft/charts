package de.slothsoft.charts;

import java.util.Arrays;
import java.util.Collection;

import de.slothsoft.charts.common.Border;

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

	/**
	 * Paints the current content onto the graphic context. Checks the instructions for
	 * what to paint. The instructions contain the area in display coordinates, starting
	 * from the top left with 0|0 and ending bottom right with something like 800|600.
	 *
	 * @param gc - graphic context; coordinates are relative to the screen
	 * @param instructions - additional instructions like the area to paint on
	 */

	public void paintOn(GraphicContext gc, PaintInstructions instructions) {
		gc.setColor(this.backgroundColor);
		gc.fillRectangle(instructions.area);

		for (final ChartPart part : fetchChartParts()) {
			part.paintOn(gc, instructions);
			instructions.setArea(part.snipNecessarySpace(instructions.getArea()));
		}
		paintGraph(gc, instructions);
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
	 * @param gc - graphic context; coordinates are relative to the screen
	 * @param instructions - additional instructions like the area to paint on
	 */

	protected abstract void paintGraph(GraphicContext gc, PaintInstructions instructions);

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
	 * @param newBackgroundColor - the color
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
	 * @param backgroundColor - the color
	 */

	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

}

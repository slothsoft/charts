package de.slothsoft.charts.linechart;

import de.slothsoft.charts.Drawable;

/**
 * A line inside the {@link LineChart}. Implementations are:
 * <ul>
 * <li>{@link DataPointLine}</li>
 * <li>{@link FunctionLine}</li>
 * </ul>
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public abstract class Line implements Drawable {

	// TODO: for all lines, make it so they are only drawn inside the graph area

	int color = 0xFF0000FF;

	/**
	 * Returns the color as ARGB int, e.g. red is <code>0xFFFF0000</code> and blue is
	 * <code>0xFF0000FF</code>.
	 *
	 * @return the color
	 */

	public int getColor() {
		return this.color;
	}

	/**
	 * Sets the color as ARGB int, e.g. red is <code>0xFFFF0000</code> and blue is
	 * <code>0xFF0000FF</code>.
	 *
	 * @param newColor - the color
	 * @return this instance
	 */

	public Line color(int newColor) {
		setColor(newColor);
		return this;
	}

	/**
	 * Sets the color as ARGB int, e.g. red is <code>0xFFFF0000</code> and blue is
	 * <code>0xFF0000FF</code>.
	 *
	 * @param color - the color
	 */

	public void setColor(int color) {
		this.color = color;
	}

}

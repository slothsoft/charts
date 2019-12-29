package de.slothsoft.charts.linechart;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.Drawable;
import de.slothsoft.charts.RefreshListener;
import de.slothsoft.charts.internal.RefreshListeners;

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

	protected static final int DEFAULT_START_X = 0;
	protected static final int DEFAULT_START_Y = 0;
	protected static final int DEFAULT_END_X = 10;
	protected static final int DEFAULT_END_Y = 10;

	protected static final Area createDefaultArea() {
		return new Area(DEFAULT_START_X, DEFAULT_START_Y, DEFAULT_END_X, DEFAULT_END_Y);
	}

	final RefreshListeners refreshListeners = new RefreshListeners(this);

	int color = 0xFF0000FF;

	/**
	 * Calculates the preferred area this line wants to be displayed in. You can use
	 * {@link Line#createDefaultArea()} for a default area.
	 *
	 * @return an area; never null
	 */

	protected abstract Area calculatePreferredArea();

	@Override
	public void addRefreshListener(RefreshListener listener) {
		this.refreshListeners.addRefreshListener(listener);
	}

	@Override
	public void removeRefreshListener(RefreshListener listener) {
		this.refreshListeners.removeRefreshListener(listener);
	}

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
	 * @param newColor the color
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
	 * @param color the color
	 */

	public void setColor(int color) {
		final int oldColor = this.color;
		this.color = color;
		if (oldColor != color) {
			this.refreshListeners.fireRefreshNeeded();
		}
	}

}

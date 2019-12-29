package de.slothsoft.charts.piechart;

import de.slothsoft.charts.RefreshListener;
import de.slothsoft.charts.internal.RefreshListeners;

/**
 * A single slice of the {@link PieChart}. These slices are entirely dependent on the
 * {@link PieChart}, so they can only be generated via {@link PieChart#addSlice(double)}
 * and {@link PieChart#addSlices(double...)}.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class PieSlice {

	final RefreshListeners refreshListeners = new RefreshListeners(this);

	double value;
	int color = 0xFF0000FF;

	/**
	 * Adds a refresh listener that is called whenever the source needs to be redrawn by
	 * the GUI.
	 *
	 * @param listener a listener
	 */

	public void addRefreshListener(RefreshListener listener) {
		this.refreshListeners.addRefreshListener(listener);
	}

	/**
	 * Removes a refresh listener that was called whenever the source needed to be redrawn
	 * by the GUI. Does nothing if the listener was never added.
	 *
	 * @param listener a listener
	 */

	public void removeRefreshListener(RefreshListener listener) {
		this.refreshListeners.removeRefreshListener(listener);
	}

	PieSlice(double value) {
		this.value = value;
	}

	/**
	 * Sets the value this slice represents.
	 *
	 * @return the value
	 */

	public double getValue() {
		return this.value;
	}

	/**
	 * Sets the value this slice represents.
	 *
	 * @param newValue the value
	 * @return this instance
	 */

	public PieSlice value(double newValue) {
		setValue(newValue);
		return this;
	}

	/**
	 * Sets the value this slice represents.
	 *
	 * @param value the value
	 */

	public void setValue(double value) {
		final double oldValue = this.value;
		this.value = value;
		if (oldValue != value) {
			this.refreshListeners.fireRefreshNeeded();
		}
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

	public PieSlice color(int newColor) {
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

	@Override
	public String toString() {
		return "PieSlice [value=" + this.value + ", color=" + this.color + "]";
	}

}

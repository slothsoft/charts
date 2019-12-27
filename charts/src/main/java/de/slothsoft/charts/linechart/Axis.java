package de.slothsoft.charts.linechart;

import de.slothsoft.charts.ChartPart;
import de.slothsoft.charts.RefreshListener;
import de.slothsoft.charts.internal.RefreshListeners;

/**
 * The X or Y axis of a {@link LineChart}.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

abstract class Axis implements ChartPart {

	final RefreshListeners refreshListeners = new RefreshListeners(this);

	int tickSteps = 1;
	int tickSize = 1;
	int bigTickSteps = 5;
	int bigTickSize = 3;
	double arrowSize = 3;

	@Override
	public void addRefreshListener(RefreshListener listener) {
		this.refreshListeners.addRefreshListener(listener);
	}

	@Override
	public void removeRefreshListener(RefreshListener listener) {
		this.refreshListeners.removeRefreshListener(listener);
	}

	/**
	 * Returns the size of the ticks in pixels.
	 *
	 * @return the tick size
	 */

	public int getTickSize() {
		return this.tickSize;
	}

	/**
	 * Sets the size of the ticks in pixels.
	 *
	 * @param newTickSize the tick size
	 * @return this instance
	 */

	public Axis tickSize(int newTickSize) {
		setTickSize(newTickSize);
		return this;
	}

	/**
	 * Sets the size of the ticks in pixels.
	 *
	 * @param tickSize the tick size
	 */

	public void setTickSize(int tickSize) {
		final int oldTickSize = this.tickSize;
		this.tickSize = tickSize;
		if (oldTickSize != tickSize) {
			this.refreshListeners.fireRefreshNeeded();
		}
	}

	/**
	 * Returns the size of the big ticks in pixels.
	 *
	 * @return the big tick size
	 */

	public int getBigTickSize() {
		return this.bigTickSize;
	}

	/**
	 * Sets the size of the big ticks in pixels.
	 *
	 * @param newBigTickSize the big tick size
	 * @return this instance
	 */

	public Axis bigTickSize(int newBigTickSize) {
		setBigTickSize(newBigTickSize);
		return this;
	}

	/**
	 * Sets the size of the big ticks in pixels.
	 *
	 * @param bigTickSize the big tick size
	 */

	public void setBigTickSize(int bigTickSize) {
		final int oldBigTickSize = this.bigTickSize;
		this.bigTickSize = bigTickSize;
		if (oldBigTickSize != bigTickSize) {
			this.refreshListeners.fireRefreshNeeded();
		}
	}

	/**
	 * Returns the range after which a big tick is painted on the graph.
	 *
	 * @return the big tick step length
	 */

	public int getBigTickSteps() {
		return this.bigTickSteps;
	}

	/**
	 * Sets the range after which a big tick is painted on the graph.
	 *
	 * @param newBigTickSteps the big tick step length
	 * @return this instance
	 */

	public Axis bigTickSteps(int newBigTickSteps) {
		setBigTickSteps(newBigTickSteps);
		return this;
	}

	/**
	 * Sets the range after which a big tick is painted on the graph.
	 *
	 * @param bigTickSteps the big tick step length
	 */

	public void setBigTickSteps(int bigTickSteps) {
		final int oldBigTickSteps = this.bigTickSteps;
		this.bigTickSteps = bigTickSteps;
		if (oldBigTickSteps != bigTickSteps) {
			this.refreshListeners.fireRefreshNeeded();
		}
	}

	/**
	 * Returns the range after which a tick is painted on the graph.
	 *
	 * @return the tick step length
	 */

	public int getTickSteps() {
		return this.tickSteps;
	}

	/**
	 * Sets the range after which a tick is painted on the graph.
	 *
	 * @param newTickSteps the tick step length
	 * @return this instance
	 */

	public Axis tickSteps(int newTickSteps) {
		setTickSteps(newTickSteps);
		return this;
	}

	/**
	 * Sets the range after which a tick is painted on the graph.
	 *
	 * @param tickSteps the tick step length
	 */

	public void setTickSteps(int tickSteps) {
		final int oldTickSteps = this.tickSteps;
		this.tickSteps = tickSteps;
		if (oldTickSteps != tickSteps) {
			this.refreshListeners.fireRefreshNeeded();
		}
	}

	/**
	 * Returns the size of the arrow at the end of the graph.
	 *
	 * @return the arrow size in pixels
	 */

	public double getArrowSize() {
		return this.arrowSize;
	}

	/**
	 * Sets the size of the arrow at the end of the graph.
	 *
	 * @param newArrowSize the arrow size in pixels
	 * @return this instance
	 */

	public Axis arrowSize(double newArrowSize) {
		setArrowSize(newArrowSize);
		return this;
	}

	/**
	 * Sets the size of the arrow at the end of the graph.
	 *
	 * @param arrowSize the arrow size in pixels
	 */

	public void setArrowSize(double arrowSize) {
		final double oldArrowSize = this.arrowSize;
		this.arrowSize = arrowSize;
		if (oldArrowSize != arrowSize) {
			this.refreshListeners.fireRefreshNeeded();
		}
	}

}

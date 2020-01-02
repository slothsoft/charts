package de.slothsoft.charts.common;

import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

import de.slothsoft.charts.Chart;
import de.slothsoft.charts.ChartPart;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.RefreshListener;
import de.slothsoft.charts.internal.RefreshListeners;

/**
 * The X or Y axis of a {@link Chart}. You have the following methods to implement this
 * abstract class:
 * <ul>
 * <li>{@link #paintHorizontalAxis(GraphicContext, double, double, double)}</li>
 * <li>{@link #paintVerticalAxis(GraphicContext, double, double, double)}</li>
 * </ul>
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public abstract class Axis implements ChartPart {

	protected final RefreshListeners refreshListeners = new RefreshListeners(this);

	int tickSteps = 1;
	int tickSize = 1;
	int bigTickSteps = 5;
	int bigTickSize = 3;
	double arrowSize = 3;

	protected DoubleUnaryOperator chartXConverter;
	protected DoubleUnaryOperator chartYConverter;

	/**
	 * Creates an axis using no converters for the coordinates.
	 */

	public Axis() {
		this(DoubleUnaryOperator.identity(), DoubleUnaryOperator.identity());
	}

	/**
	 * Creates an axis using converters for the coordinates.
	 *
	 * @param chartXConverter converter for graph x to chart x
	 * @param chartYConverter converter for graph y to chart y
	 */

	public Axis(DoubleUnaryOperator chartXConverter, DoubleUnaryOperator chartYConverter) {
		this.chartXConverter = Objects.requireNonNull(chartXConverter);
		this.chartYConverter = Objects.requireNonNull(chartYConverter);
	}

	/**
	 * Paints the horizontal axis using the chart.
	 *
	 * @param gc the graphic context to draw on
	 * @param graphStartX the graph's start x
	 * @param graphEndX the graph's end x
	 * @param y the y coordinate this axis get drawn on (chart coordinate!)
	 */

	protected void paintHorizontalAxis(GraphicContext gc, double graphStartX, double graphEndX, double y) {
		final double xMin = this.chartXConverter.applyAsDouble(graphStartX);
		final double xMax = this.chartXConverter.applyAsDouble(graphEndX);

		// paint the line

		gc.setColor(0xFF000000);
		gc.drawLine(xMin, y, xMax, y);

		// paint the big and little ticks

		final int end = (int) Math.ceil(graphEndX);
		for (int i = (int) Math.floor(graphStartX); i < end; i++) {
			final double x = this.chartXConverter.applyAsDouble(i);
			if (i % this.tickSteps == 0) {
				gc.drawLine(x, y - this.tickSize, x, y + this.tickSize);
			}
			if (i % this.bigTickSteps == 0) {
				gc.drawLine(x, y - this.bigTickSize, x, y + this.bigTickSize);
			}
		}

		// paint the arrow at the end

		final double[] arrowX = {xMax, xMax - this.arrowSize, xMax - this.arrowSize};
		final double[] arrowY = {y, y + this.arrowSize, y - this.arrowSize};
		gc.fillPolygon(arrowX, arrowY);
	}

	/**
	 * Paints the vertical axis using the chart.
	 *
	 * @param gc the graphic context to draw on
	 * @param graphStartX the graph's start y
	 * @param graphEndY the graph's end y
	 * @param x the y coordinate this axis get drawn on (chart coordinate!)
	 */
	protected void paintVerticalAxis(GraphicContext gc, double graphStartX, double graphEndY, double x) {
		final double yMin = this.chartYConverter.applyAsDouble(graphStartX);
		final double yMax = this.chartYConverter.applyAsDouble(graphEndY);

		// paint the line

		gc.setColor(0xFF000000);
		gc.drawLine(x, yMin, x, yMax);

		// paint the big and little ticks

		final int end = (int) Math.ceil(graphEndY);
		for (int i = (int) Math.floor(graphStartX); i < end; i++) {
			final double y = this.chartYConverter.applyAsDouble(i);
			if (i % this.tickSteps == 0) {
				gc.drawLine(x - this.tickSize, y, x + this.tickSize, y);
			}
			if (i % this.bigTickSteps == 0) {
				gc.drawLine(x - this.bigTickSize, y, x + this.bigTickSize, y);
			}
		}

		// paint the arrow at the end

		final double[] arrowX = {x, x + this.arrowSize, x - this.arrowSize};
		final double[] arrowY = {yMax - this.arrowSize, yMax, yMax};
		gc.fillPolygon(arrowX, arrowY);
	}
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

package de.slothsoft.charts.linechart;

import java.util.Objects;
import java.util.function.DoubleConsumer;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;

/**
 * The X axis of a {@link LineChart}.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class XAxis extends Axis {

	/**
	 * The position of the X axis.
	 */

	public enum Position {
		/**
		 * The default behavior is to have the axis on y=0 if that is visible, else on the
		 * top or bottom border of the graph depending were it is in relation to the
		 * visible area.
		 */
		DEFAULT {
			@Override
			void paintAxis(DoubleConsumer axisPainter, double yMin, double y0, double yMax) {
				axisPainter.accept(Math.max(Math.min(y0, yMin), yMax));
			}
		},
		/** Displays the axis on the top of the visible graph area. */
		TOP {
			@Override
			void paintAxis(DoubleConsumer axisPainter, double yMin, double y0, double yMax) {
				axisPainter.accept(yMax);
			}
		},
		/** Displays the axis on the bottom of the visible graph area. */
		BOTTOM {
			@Override
			void paintAxis(DoubleConsumer axisPainter, double yMin, double y0, double yMax) {
				axisPainter.accept(yMin);
			}
		},
		/** Displays the axis on the top and bottom of the visible graph area. */
		TOP_AND_BOTTOM {
			@Override
			void paintAxis(DoubleConsumer axisPainter, double yMin, double y0, double yMax) {
				axisPainter.accept(yMin);
				axisPainter.accept(yMax);
			}
		},
		/** Displays the axis on y=0. */
		Y0 {
			@Override
			void paintAxis(DoubleConsumer axisPainter, double yMin, double y0, double yMax) {
				axisPainter.accept(y0);
			}
		};

		abstract void paintAxis(DoubleConsumer axisPainter, double yMin, double y0, double yMax);

	}

	private final LineChart chart;
	private XAxis.Position position = XAxis.Position.DEFAULT;

	/**
	 * Constructor.
	 *
	 * @param chart the chart this axis belongs to
	 */

	public XAxis(LineChart chart) {
		this.chart = Objects.requireNonNull(chart);
	}

	@Override
	public void paintOn(GraphicContext gc, PaintInstructions instructions) {
		final Area area = this.chart.calculateDisplayedArea();
		final double y0 = this.chart.convertToChartY(0);
		final double xMin = this.chart.convertToChartX(area.getStartX());
		final double xMax = this.chart.convertToChartX(area.getEndX());

		final DoubleConsumer axisPainter = y -> {
			// paint the line

			gc.setColor(0xFF000000);
			gc.drawLine(xMin, y, xMax, y);

			// paint the big and little ticks

			final int end = (int) Math.ceil(area.getEndX());
			for (int i = (int) Math.floor(area.getStartX()); i < end; i++) {
				final double x = this.chart.convertToChartX(i);
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
		};

		final double yMin = this.chart.convertToChartY(area.getStartY());
		final double yMax = this.chart.convertToChartY(area.getEndY());
		this.position.paintAxis(axisPainter, yMin, y0, yMax);
	}

	/**
	 * Returns the position of the x axis.
	 *
	 * @return the position; never null
	 */

	public XAxis.Position getPosition() {
		return this.position;
	}

	/**
	 * Sets the position of the x axis.
	 *
	 * @param newPosition the position; cannot be null
	 * @return this instance
	 */

	public XAxis position(XAxis.Position newPosition) {
		setPosition(newPosition);
		return this;
	}

	/**
	 * Sets the position of the x axis.
	 *
	 * @param position the position; cannot be null
	 */

	public void setPosition(XAxis.Position position) {
		final XAxis.Position oldPosition = this.position;
		this.position = Objects.requireNonNull(position);
		if (oldPosition != position) {
			this.refreshListeners.fireRefreshNeeded();
		}
	}

}

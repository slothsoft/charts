package de.slothsoft.charts.linechart;

import java.util.Objects;
import java.util.function.DoubleConsumer;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;

/**
 * The Y axis of a {@link LineChart}.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class YAxis extends Axis {

	/**
	 * The position of the Y axis.
	 */

	public enum Position {
		/**
		 * The default behavior is to have the axis on x=0 if that is visible, else on the
		 * left or right border of the graph depending were it is in relation to the
		 * visible area.
		 */
		DEFAULT {
			@Override
			void paintAxis(DoubleConsumer axisPainter, double xMin, double x0, double xMax) {
				axisPainter.accept(Math.min(Math.max(x0, xMin), xMax));
			}
		},
		/** Displays the axis on the right of the visible graph area. */
		RIGHT {
			@Override
			void paintAxis(DoubleConsumer axisPainter, double xMin, double x0, double xMax) {
				axisPainter.accept(xMax);
			}
		},
		/** Displays the axis on the left of the visible graph area. */
		LEFT {
			@Override
			void paintAxis(DoubleConsumer axisPainter, double xMin, double x0, double xMax) {
				axisPainter.accept(xMin);
			}
		},
		/** Displays the axis on the left and right of the visible graph area. */
		LEFT_AND_RIGHT {
			@Override
			void paintAxis(DoubleConsumer axisPainter, double xMin, double x0, double xMax) {
				axisPainter.accept(xMin);
				axisPainter.accept(xMax);
			}
		},
		/** Displays the axis on x=0. */
		X0 {
			@Override
			void paintAxis(DoubleConsumer axisPainter, double xMin, double x0, double xMax) {
				axisPainter.accept(x0);
			}
		};

		abstract void paintAxis(DoubleConsumer axisPainter, double xMin, double x0, double xMax);

	}

	private final LineChart chart;
	private YAxis.Position position = YAxis.Position.DEFAULT;

	/**
	 * Constructor.
	 *
	 * @param chart the chart this axis belongs to
	 */

	public YAxis(LineChart chart) {
		this.chart = Objects.requireNonNull(chart);
	}

	@Override
	public void paintOn(GraphicContext gc, PaintInstructions instructions) {
		final Area area = this.chart.calculateDisplayedArea();
		final double x0 = this.chart.convertToChartX(0);
		final double yMin = this.chart.convertToChartY(area.getStartY());
		final double yMax = this.chart.convertToChartY(area.getEndY());

		final DoubleConsumer axisPainter = x -> {
			// paint the line

			gc.setColor(0xFF000000);
			gc.drawLine(x, yMin, x, yMax);

			// paint the big and little ticks

			final int end = (int) Math.ceil(area.getEndY());
			for (int i = (int) Math.floor(area.getStartY()); i < end; i++) {
				final double y = this.chart.convertToChartY(i);
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
		};

		final double xMin = this.chart.convertToChartX(area.getStartX());
		final double xMax = this.chart.convertToChartX(area.getEndX());
		this.position.paintAxis(axisPainter, xMin, x0, xMax);
	}

	/**
	 * Returns the position of the y axis.
	 *
	 * @return the position; never null
	 */

	public YAxis.Position getPosition() {
		return this.position;
	}

	/**
	 * Sets the position of the y axis.
	 *
	 * @param newPosition the position; cannot be null
	 * @return this instance
	 */

	public YAxis position(YAxis.Position newPosition) {
		setPosition(newPosition);
		return this;
	}

	/**
	 * Sets the position of the y axis.
	 *
	 * @param position the position; cannot be null
	 */

	public void setPosition(YAxis.Position position) {
		this.position = Objects.requireNonNull(position);
	}
}

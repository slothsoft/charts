package de.slothsoft.charts.linechart;

import java.util.Objects;
import java.util.function.DoubleConsumer;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;
import de.slothsoft.charts.common.Axis;

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
		},
		/**
		 * Does not displays the axis.
		 *
		 * @since 0.2.0
		 */
		NOWHERE {
			@Override
			void paintAxis(DoubleConsumer axisPainter, double yMin, double y0, double yMax) {
				// Don't do it.
			}
		};

		abstract void paintAxis(DoubleConsumer axisPainter, double xMin, double x0, double xMax);

	}

	private final LineChart chart;
	private YAxis.Position position = YAxis.Position.DEFAULT;

	/**
	 * Constructor for a {@link LineChart}.
	 *
	 * @param chart the chart this axis belongs to
	 */

	public YAxis(LineChart chart) {
		super(chart::convertToChartX, chart::convertToChartY);
		this.chart = chart;
	}

	@Override
	public void paintOn(GraphicContext gc, PaintInstructions instructions) {
		final Area area = this.chart.calculateDisplayedArea();
		final double x0 = this.chartXConverter.applyAsDouble(0);
		final double xMin = this.chartXConverter.applyAsDouble(area.getStartX());
		final double xMax = this.chartXConverter.applyAsDouble(area.getEndX());
		this.position.paintAxis(x -> paintVerticalAxis(gc, area.getStartY(), area.getEndY(), x), xMin, x0, xMax);
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
		final YAxis.Position oldPosition = this.position;
		this.position = Objects.requireNonNull(position);
		if (oldPosition != position) {
			this.refreshListeners.fireRefreshNeeded();
		}
	}
}

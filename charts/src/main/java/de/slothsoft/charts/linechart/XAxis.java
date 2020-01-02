package de.slothsoft.charts.linechart;

import java.util.Objects;
import java.util.function.DoubleConsumer;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;
import de.slothsoft.charts.common.Axis;

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

		abstract void paintAxis(DoubleConsumer axisPainter, double yMin, double y0, double yMax);

	}

	private final LineChart chart;
	private XAxis.Position position = XAxis.Position.DEFAULT;

	/**
	 * Constructor for a {@link LineChart}.
	 *
	 * @param chart the chart this axis belongs to
	 */

	public XAxis(LineChart chart) {
		super(chart::convertToChartX, chart::convertToChartY);
		this.chart = chart;
	}

	@Override
	public void paintOn(GraphicContext gc, PaintInstructions abc) {
		final Area area = this.chart.calculateDisplayedArea();
		final double y0 = this.chartYConverter.applyAsDouble(0);
		final double yMin = this.chartYConverter.applyAsDouble(area.getStartY());
		final double yMax = this.chartYConverter.applyAsDouble(area.getEndY());
		this.position.paintAxis(y -> paintHorizontalAxis(gc, area.getStartX(), area.getEndX(), y), yMin, y0, yMax);
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

package de.slothsoft.charts.linechart;

import java.util.Objects;

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
	 * Constructor.
	 *
	 * @param chart the chart this axis belongs to
	 */

	public YAxis(LineChart chart) {
		super(Objects.requireNonNull(chart));
	}

	@Override
	public void paintOn(GraphicContext gc, PaintInstructions instructions) {
		final Area area = this.chart.calculateDisplayedArea();
		final double x0 = this.chart.convertToChartX(0);
		final double yMin = this.chart.convertToChartY(area.getStartY());
		final double yMax = this.chart.convertToChartY(area.getEndY());

		// paint the line

		gc.setColor(0xFF000000);
		gc.drawLine(x0, yMin, x0, yMax);

		// paint the big and little ticks

		final int end = (int) Math.ceil(area.getEndY());
		for (int i = (int) Math.floor(area.getStartY()); i < end; i++) {
			final double y = this.chart.convertToChartY(i);
			if (i % this.tickSteps == 0) {
				gc.drawLine(x0 - this.tickSize, y, x0 + this.tickSize, y);
			}
			if (i % this.bigTickSteps == 0) {
				gc.drawLine(x0 - this.bigTickSize, y, x0 + this.bigTickSize, y);
			}
		}

		// paint the arrow at the end

		final double[] arrowX = {x0, x0 + this.arrowSize, x0 - this.arrowSize};
		final double[] arrowY = {yMax - this.arrowSize, yMax, yMax};
		gc.fillPolygon(arrowX, arrowY);
	}
}

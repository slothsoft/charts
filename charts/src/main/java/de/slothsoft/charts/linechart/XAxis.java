package de.slothsoft.charts.linechart;

import java.util.Objects;

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
	 * Constructor.
	 *
	 * @param chart the chart this axis belongs to
	 */

	public XAxis(LineChart chart) {
		super(Objects.requireNonNull(chart));
	}

	@Override
	public void paintOn(GraphicContext gc, PaintInstructions instructions) {
		final Area area = this.chart.calculateDisplayedArea();
		final double y0 = this.chart.convertToChartY(0);
		final double xMin = this.chart.convertToChartX(area.getStartX());
		final double xMax = this.chart.convertToChartX(area.getEndX());

		// paint the line

		gc.setColor(0xFF000000);
		gc.drawLine(xMin, y0, xMax, y0);

		// paint the big and little ticks

		final int end = (int) Math.ceil(area.getEndX());
		for (int i = (int) Math.floor(area.getStartX()); i < end; i++) {
			final double x = this.chart.convertToChartX(i);
			if (i % this.tickSteps == 0) {
				gc.drawLine(x, y0 - this.tickSize, x, y0 + this.tickSize);
			}
			if (i % this.bigTickSteps == 0) {
				gc.drawLine(x, y0 - this.bigTickSize, x, y0 + this.bigTickSize);
			}
		}

		// paint the arrow at the end

		final double[] arrowX = {xMax, xMax - this.arrowSize, xMax - this.arrowSize};
		final double[] arrowY = {y0, y0 + this.arrowSize, y0 - this.arrowSize};
		gc.fillPolygon(arrowX, arrowY);
	}
}

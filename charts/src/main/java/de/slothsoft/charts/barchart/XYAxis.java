package de.slothsoft.charts.barchart;

import java.util.function.DoubleUnaryOperator;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;
import de.slothsoft.charts.common.Axis;

/**
 * The X or Y axis of a {@link BarChart}.
 *
 * @author Stef Schulz
 * @since 0.2.0
 */

public class XYAxis extends Axis {

	private final BarChart chart;

	/**
	 * Constructor for a {@link BarChart}.
	 *
	 * @param chart the chart this axis belongs to
	 */

	public XYAxis(BarChart chart) {
		super(DoubleUnaryOperator.identity(), DoubleUnaryOperator.identity());
		this.chart = chart;
	}

	@Override
	public void paintOn(GraphicContext gc, PaintInstructions instructions) {
		final Area area = instructions.getArea();
		final double maxValue = this.chart.bars.stream().mapToDouble(Bar::getValue).max().orElse(1);
		this.chartYConverter = y -> area.getStartY() + (maxValue - y) * area.calculateHeight() / maxValue;
		final double startX = area.getStartX();
		paintVerticalAxis(gc, 0, maxValue, startX);
		gc.drawLine(startX, area.getEndY(), area.calculateWidth(), area.getEndY());
	}
}

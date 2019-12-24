package de.slothsoft.charts.linechart;

import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.internal.DelegatingGraphicContext;

/**
 * On the regular screen the point 0|0 is on the top left and x gets bigger going right
 * while y gets bigger going down. In a line chart x is the same but y goes up. So we flip
 * y so it's easier to draw?
 */

class FlipYGraphicContext extends DelegatingGraphicContext {

	public FlipYGraphicContext(GraphicContext delegate) {
		super(delegate);
	}

	@Override
	public void translate(double x, double y) {
		super.translate(x, -y);
	}

	@Override
	public void scale(double x, double y) {
		super.scale(x, -y);
	}

	@Override
	public void drawLine(double x1, double y1, double x2, double y2) {
		super.drawLine(x1, -y1, x2, -y2);
	}

	@Override
	public void fillRectangle(double x, double y, double width, double height) {
		super.fillRectangle(x, -y - height, width, height);
	}

}

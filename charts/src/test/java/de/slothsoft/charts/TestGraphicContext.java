package de.slothsoft.charts;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation to test stuff, e.g. to test default methods.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class TestGraphicContext implements GraphicContext {

	public List<String> log = new ArrayList<>();

	private int color;

	@Override
	public void setColor(int color) {
		this.log.add("setColor(" + color + ")");
		this.color = color;
	}

	@Override
	public int getColor() {
		this.log.add("getColor()");
		return this.color;
	}

	@Override
	public void translate(double x, double y) {
		this.log.add("translate(" + x + ", " + y + ")");
	}

	@Override
	public void scale(double x, double y) {
		this.log.add("scale(" + x + ", " + y + ")");
	}

	@Override
	public void clip(Area rect) {
		this.log.add("clip(" + rect + ")");
	}

	@Override
	public void fillRectangle(double x, double y, double width, double height) {
		this.log.add("fillRectangle(" + x + ", " + y + ", " + width + ", " + height + ")");

	}

	@Override
	public void drawPolyline(double[] x, double[] y) {
		this.log.add("drawPolyline(" + toString(x) + ", " + toString(y) + ")");
	}

	private static String toString(double[] array) {
		final StringBuilder result = new StringBuilder("[");
		for (final double value : array) {
			if (result.length() > 1) {
				result.append(", ");
			}
			result.append(value);
		}
		result.append("]");
		return result.toString();
	}

}

package de.slothsoft.charts.internal;

import java.util.ArrayList;
import java.util.List;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.Font;
import de.slothsoft.charts.GraphicContext;

/**
 * An implementation to test stuff, e.g. to test default methods.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class LogGraphicContext implements GraphicContext {

	List<String> log = new ArrayList<>();

	private int color;
	private Font font = Font.NORMAL;

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
	public Font getFont() {
		this.log.add("getFont()");
		return this.font;
	}

	@Override
	public void setFont(Font font) {
		this.log.add("setFont(" + font + ")");
		this.font = font;
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

	@Override
	public void fillPolygon(double[] x, double[] y) {
		this.log.add("fillPolygon(" + toString(x) + ", " + toString(y) + ")");
	}

	@Override
	public void drawText(double x, double y, String text) {
		this.log.add("drawText(" + x + ", " + y + ", " + text + ")");
	}

	@Override
	public void fillOval(double x, double y, double width, double height) {
		this.log.add("fillOval(" + x + ", " + y + ", " + width + ", " + height + ")");
	}

	@Override
	public void fillArc(double x, double y, double width, double height, double startAngle, double arcAngle) {
		this.log.add(
				"fillArc(" + x + ", " + y + ", " + width + ", " + height + ", " + startAngle + ", " + arcAngle + ")");
	}

	@Override
	public Area calculateTextSize(String text) {
		this.log.add("calculateTextSize(" + text + ")");
		return new Area(text.length() * this.font.getSize(), this.font.getSize());
	}

	public List<String> getLog() {
		return this.log;
	}
}

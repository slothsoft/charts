package de.slothsoft.charts.swing;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.Objects;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.Font;
import de.slothsoft.charts.GraphicContext;

/**
 * A {@link GraphicContext} implementation using the Swing {@link Graphics2D} to draw on
 * whatever you like.
 *
 * @author Stef Schulz
 * @since 0.2.0
 */

public class Graphics2DGraphicContext implements GraphicContext {

	private Graphics2D delegate;

	private Font fontAsEnum;

	private double scaleX = 1;
	private double scaleY = 1;

	public Graphics2DGraphicContext(Graphics2D graphics) {
		this.delegate = Objects.requireNonNull(graphics);
	}

	@Override
	public void setColor(int color) {
		this.delegate.setColor(new Color(color));
	}

	@Override
	public int getColor() {
		return this.delegate.getColor().getRGB();
	}

	@Override
	public void setFont(Font fontAsEnum) {
		this.fontAsEnum = fontAsEnum;
		this.delegate.setFont(this.delegate.getFont().deriveFont(createFontStyle(fontAsEnum), fontAsEnum.getSize()));
	}

	private static int createFontStyle(Font enumFont) {
		int result = 0;
		if (enumFont.isBold()) {
			result |= java.awt.Font.BOLD;
		}
		return result;
	}

	@Override
	public Font getFont() {
		return this.fontAsEnum;
	}

	@Override
	public Area calculateTextSize(String text) {
		final FontMetrics fontMetrics = this.delegate.getFontMetrics();
		return new Area(fontMetrics.stringWidth(text), fontMetrics.getHeight());
	}

	@Override
	public void translate(double x, double y) {
		this.delegate.translate(x, y);
	}

	@Override
	public void scale(double x, double y) {
		this.scaleX *= x;
		this.scaleY *= y;
	}

	@Override
	public void clip(Area rect) {
		if (rect == null) {
			this.delegate.setClip(null);
		} else {
			this.delegate.setClip(convertToX(rect.getStartX()), convertToY(rect.getStartY()),
					convertToX(rect.calculateWidth()), convertToY(rect.calculateHeight()));
		}
	}

	private int convertToX(double x) {
		return (int) (this.scaleX * x);
	}

	private int convertToY(double Y) {
		return (int) (this.scaleY * Y);
	}

	@Override
	public void drawLine(double x1, double y1, double x2, double y2) {
		this.delegate.drawLine(convertToX(x1), convertToY(y1), convertToX(x2), convertToX(y2));
	}

	@Override
	public void fillRectangle(double x, double y, double width, double height) {
		this.delegate.fillRect(convertToX(x), convertToY(y), convertToX(width), convertToY(height));
	}

	@Override
	public void drawPolyline(double[] x, double[] y) {
		this.delegate.drawPolyline(toIntArrayX(x), toIntArrayY(y), x.length);
	}

	private int[] toIntArrayX(double[] input) {
		final int[] result = new int[input.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = convertToX(input[i]);
		}
		return result;
	}

	private int[] toIntArrayY(double[] input) {
		final int[] result = new int[input.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = convertToY(input[i]);
		}
		return result;
	}

	@Override
	public void fillPolygon(double[] x, double[] y) {
		this.delegate.fillPolygon(toIntArrayX(x), toIntArrayY(y), x.length);
	}

	@Override
	public void drawText(double x, double y, String text) {
		this.delegate.drawString(text, convertToX(x), convertToY(y) + this.delegate.getFontMetrics().getAscent());
	}

	@Override
	public void fillOval(double x, double y, double width, double height) {
		this.delegate.fillOval(convertToX(x), convertToY(y), convertToX(width), convertToY(height));
	}

	@Override
	public void fillArc(double x, double y, double width, double height, double startAngle, double arcAngle) {
		this.delegate.fillArc(convertToX(x), convertToY(y), convertToX(width), convertToY(height), (int) startAngle,
				(int) arcAngle);
	}

	/**
	 * Returns the Graphics2D.
	 *
	 * @return the Graphics2D to draw on; never null
	 */

	public Graphics2D getDelegate() {
		return this.delegate;
	}

	/**
	 * Sets the Graphics2D.
	 *
	 * @param newDelegate the Graphics2D to draw on; cannot be null
	 * @return this instance
	 */

	public Graphics2DGraphicContext delegate(Graphics2D newDelegate) {
		setDelegate(newDelegate);
		return this;
	}

	/**
	 * Sets the Graphics2D.
	 *
	 * @param delegate the Graphics2D to draw on; cannot be null
	 */

	public void setDelegate(Graphics2D delegate) {
		this.delegate = Objects.requireNonNull(delegate);
	}

}

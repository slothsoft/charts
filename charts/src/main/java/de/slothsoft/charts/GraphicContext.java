package de.slothsoft.charts;

/**
 * Something you can draw on. This is used to have different frameworks interact with this
 * library. Some notes:
 *
 * <ul>
 * <li><code>drawX</code> - means that {@link #getColor()} will be used as the border of
 * the figure X</li>
 * <li><code>fillX</code> - means that {@link #getColor()} will be used as the area of the
 * figure X</li>
 * </ul>
 *
 * Classes are connected like this: <br>
 * <img src="https://raw.githubusercontent.com/wiki/slothsoft/charts/images/UML.png" alt=
 * "UML Diagram">
 */

public interface GraphicContext {

	// TODO: these methods are missing to make the existing ones complete:
	// void drawOval(double x, double y, double width, double height);
	// void drawArc(double x, double y, double width, double height, double start, double
	// end);

	// ======
	// CONFIG
	// ======

	/**
	 * Sets the color as ARGB int, e.g. red is <code>0xFFFF0000</code> and blue is
	 * <code>0xFF0000FF</code>.
	 *
	 * @param color the color
	 */

	void setColor(int color);

	/**
	 * Returns the color as ARGB int, e.g. red is <code>0xFFFF0000</code> and blue is
	 * <code>0xFF0000FF</code>.
	 *
	 * @return the color
	 */

	int getColor();

	/**
	 * Sets the font as an enum with the font properties as hints for the actual font used
	 * by the GUI.
	 *
	 * @param font the font
	 */

	void setFont(Font font);

	/**
	 * Returns the font as an enum with the font properties as hints for the actual font
	 * used by the GUI.
	 *
	 * @return the font
	 */

	Font getFont();

	/**
	 * Calculates the size of the text.
	 *
	 * @param text
	 * @return the text size area
	 */

	Area calculateTextSize(String text);

	// TODO: this area should be a point or something?
	// - also LineChart.convertToGraphCoordinates

	// ==============
	// ADVANCED STUFF
	// ==============

	/**
	 * Moves the drawing canvas's origin to the new coordinates.
	 *
	 * @param x translate x
	 * @param y translate y
	 */

	void translate(double x, double y);

	/**
	 * Scales the drawing canvas with the factor.
	 *
	 * @param factor factor for both x and y
	 */

	default void scale(double factor) {
		scale(factor, factor);
	}

	/**
	 * Scales the drawing canvas with the factor.
	 *
	 * @param x factor x
	 * @param y factor y
	 */

	void scale(double x, double y);

	/**
	 * Restricts which area of the context can be used to drawn on. Set to
	 * <code>null</code> to remove the restriction again.
	 *
	 * @param rect the clipping area
	 */

	void clip(Area rect);

	// ===================
	// STUFF TO PAINT WITH
	// ===================

	/**
	 * Draws a simple line between two points.
	 *
	 * @param x1 - start point x
	 * @param y1 - start point y
	 * @param x2 - end point x
	 * @param y2 - end point y
	 */

	default void drawLine(double x1, double y1, double x2, double y2) {
		drawPolyline(new double[]{x1, x2}, new double[]{y1, y2});
	}

	/**
	 * Paints a filled rectangle.
	 *
	 * @param rect the rectangle's coordinates
	 */

	default void fillRectangle(Area rect) {
		fillRectangle(rect.startX, rect.startY, rect.endX - rect.startX, rect.endY - rect.startY);
	}

	/**
	 * Paints a filled rectangle.
	 *
	 * @param x the rectangle's x
	 * @param y the rectangle's y
	 * @param width the rectangle's width
	 * @param height the rectangle's height
	 */

	default void fillRectangle(double x, double y, double width, double height) {
		fillPolygon(new double[]{x, x + width, x + width, x, x}, new double[]{y, y, y + height, y + height, y});
	}

	/**
	 * Paints a outline of a rectangle.
	 *
	 * @param rect the rectangle's coordinates
	 * @since 0.2.0
	 */

	default void drawRectangle(Area rect) {
		drawRectangle(rect.startX, rect.startY, rect.endX - rect.startX, rect.endY - rect.startY);
	}

	/**
	 * Paints a outline of a rectangle.
	 *
	 * @param x the rectangle's x
	 * @param y the rectangle's y
	 * @param width the rectangle's width
	 * @param height the rectangle's height
	 * @since 0.2.0
	 */

	default void drawRectangle(double x, double y, double width, double height) {
		drawPolyline(new double[]{x, x + width, x + width, x, x}, new double[]{y, y, y + height, y + height, y});
	}

	/**
	 * Paints the outline of a polyline.
	 *
	 * @param x the polygon's x coordinates
	 * @param y the polygon's y coordinates
	 * @see #fillPolygon(double[], double[])
	 */

	void drawPolyline(double[] x, double[] y);

	/**
	 * Paints a filled polygon.
	 *
	 * @param x the polygon's x coordinates
	 * @param y the polygon's y coordinates
	 * @see #drawPolyline(double[], double[])
	 */

	void fillPolygon(double[] x, double[] y);

	/**
	 * Paints the text on a position.
	 *
	 * @param x the text's x
	 * @param y the text's y
	 */

	void drawText(double x, double y, String text);

	/**
	 * Paints a filled oval.
	 *
	 * @param x the oval's x
	 * @param y the oval's y
	 * @param width the oval's width
	 * @param height the oval's height
	 */

	void fillOval(double x, double y, double width, double height);

	/**
	 * Draws the outline of a circular or elliptical arc covering the specified rectangle.
	 * <p>
	 * The resulting arc begins at <code>startAngle</code> and extends for
	 * <code>arcAngle</code> degrees, using the current color. Angles are interpreted such
	 * that 0&nbsp;degrees is at the 3&nbsp;o'clock position. A positive value indicates a
	 * counter-clockwise rotation while a negative value indicates a clockwise rotation.
	 * <p>
	 * The center of the arc is the center of the rectangle whose origin is
	 * (<i>x</i>,&nbsp;<i>y</i>) and whose size is specified by the <code>width</code> and
	 * <code>height</code> arguments.
	 * <p>
	 * The resulting arc covers an area <code>width&nbsp;+&nbsp;1</code> pixels wide by
	 * <code>height&nbsp;+&nbsp;1</code> pixels tall.
	 * <p>
	 *
	 * @param x the <i>x</i> coordinate of the upper-left corner of the arc to be drawn.
	 * @param y the <i>y</i> coordinate of the upper-left corner of the arc to be drawn.
	 * @param width the width of the arc to be drawn.
	 * @param height the height of the arc to be drawn.
	 * @param startAngle the beginning angle.
	 * @param arcAngle the angular extent of the arc, relative to the start angle.
	 */

	void fillArc(double x, double y, double width, double height, double startAngle, double arcAngle);

}

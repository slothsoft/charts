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
	// void drawRectangle(Rectangle rect);
	// void drawRectangle(double x, double y, double width, double height);
	// void fillPolygon(double[] x, double[] y);

	// ======
	// CONFIG
	// ======

	/**
	 * Sets the color as ARGB int, e.g. red is <code>0xFFFF0000</code> and blue is
	 * <code>0xFF0000FF</code>.
	 *
	 * @param color - the color
	 */

	void setColor(int color);

	/**
	 * Returns the color as ARGB int, e.g. red is <code>0xFFFF0000</code> and blue is
	 * <code>0xFF0000FF</code>.
	 *
	 * @return the color
	 */

	int getColor();

	// ==============
	// ADVANCED STUFF
	// ==============

	/**
	 * Moves the drawing canvas's origin to the new coordinates.
	 *
	 * @param x - translate x
	 * @param y - translate y
	 */

	void translate(double x, double y);

	/**
	 * Scales the drawing canvas with the factor.
	 *
	 * @param factor - factor for both x and y
	 */

	default void scale(double factor) {
		scale(factor, factor);
	}

	/**
	 * Scales the drawing canvas with the factor.
	 *
	 * @param x - factor x
	 * @param y - factor y
	 */

	void scale(double x, double y);

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

	void drawLine(double x1, double y1, double x2, double y2);

	/**
	 * Paints a filled rectangle.
	 *
	 * @param rect - the rectangle's coordinates
	 */

	default void fillRectangle(Rectangle rect) {
		fillRectangle(rect.x, rect.y, rect.width, rect.height);
	}

	/**
	 * Paints a filled rectangle.
	 *
	 * @param x - the rectangle's x
	 * @param y - the rectangle's y
	 * @param width - the rectangle's width
	 * @param height - the rectangle's height
	 */

	void fillRectangle(double x, double y, double width, double height);

	/**
	 * Paints the outline of a polygon.
	 *
	 * @param x - the polygon's x coordinates
	 * @param y - the polygon's y coordinates
	 */

	void drawPolygon(double[] x, double[] y);

}

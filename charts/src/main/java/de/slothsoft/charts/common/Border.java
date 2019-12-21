package de.slothsoft.charts.common;

import de.slothsoft.charts.Chart;
import de.slothsoft.charts.ChartPart;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;
import de.slothsoft.charts.Rectangle;

/**
 * A border on a {@link Chart}. On default it's only empty space and not visible.
 */

public class Border implements ChartPart {

	private int spaceOnTop = 10;
	private int spaceOnLeft = 5;
	private int spaceOnRight = 5;
	private int spaceOnBottom = 7;

	@Override
	public void paintOn(GraphicContext gc, PaintInstructions instructions) {
		// this border is only empty space so don't draw anything
	}

	@Override
	public Rectangle snipNecessarySpace(Rectangle existingArea) {
		return new Rectangle().x(existingArea.getX() + this.spaceOnLeft).y(existingArea.getY() + this.spaceOnTop)
				.width(existingArea.getWidth() - this.spaceOnLeft - this.spaceOnRight)
				.height(existingArea.getHeight() - this.spaceOnTop - this.spaceOnBottom);
	}

	/**
	 * Sets the border size in all four directions.
	 *
	 * @param newSpace - the space
	 * @return this instance
	 */

	public Border space(int newSpace) {
		setSpace(newSpace);
		return this;
	}

	/**
	 * Sets the border size in all four directions.
	 *
	 * @param space - the space
	 */

	public void setSpace(int space) {
		this.spaceOnBottom = space;
		this.spaceOnTop = space;
		this.spaceOnRight = space;
		this.spaceOnLeft = space;
	}

	/**
	 * Return the border size on the bottom.
	 *
	 * @return the space
	 */

	public int getSpaceOnBottom() {
		return this.spaceOnBottom;
	}

	/**
	 * Sets the border size on the bottom.
	 *
	 * @param newSpaceOnBottom - the space
	 * @return this instance
	 */

	public Border spaceOnBottom(int newSpaceOnBottom) {
		setSpaceOnBottom(newSpaceOnBottom);
		return this;
	}

	/**
	 * Sets the border size on the bottom.
	 *
	 * @param spaceOnBottom - the space
	 */

	public void setSpaceOnBottom(int spaceOnBottom) {
		this.spaceOnBottom = spaceOnBottom;
	}

	/**
	 * Return the border size on the left.
	 *
	 * @return the space
	 */

	public int getSpaceOnLeft() {
		return this.spaceOnLeft;
	}

	/**
	 * Sets the border size on the left.
	 *
	 * @param newSpaceOnLeft - the space
	 * @return this instance
	 */

	public Border spaceOnLeft(int newSpaceOnLeft) {
		setSpaceOnLeft(newSpaceOnLeft);
		return this;
	}

	/**
	 * Sets the border size on the left.
	 *
	 * @param spaceOnLeft - the space
	 */

	public void setSpaceOnLeft(int spaceOnLeft) {
		this.spaceOnLeft = spaceOnLeft;
	}

	/**
	 * Return the border size on the right.
	 *
	 * @return the space
	 */

	public int getSpaceOnRight() {
		return this.spaceOnRight;
	}

	/**
	 * Sets the border size on the right.
	 *
	 * @param newSpaceOnRight - the space
	 * @return this instance
	 */

	public Border spaceOnRight(int newSpaceOnRight) {
		setSpaceOnRight(newSpaceOnRight);
		return this;
	}

	/**
	 * Sets the border size on the right.
	 *
	 * @param spaceOnRight - the space
	 */

	public void setSpaceOnRight(int spaceOnRight) {
		this.spaceOnRight = spaceOnRight;
	}

	/**
	 * Return the border size on the top.
	 *
	 * @return the space
	 */

	public int getSpaceOnTop() {
		return this.spaceOnTop;
	}

	/**
	 * Sets the border size on the top.
	 *
	 * @param newSpaceOnTop - the space
	 * @return this instance
	 */

	public Border spaceOnTop(int newSpaceOnTop) {
		setSpaceOnTop(newSpaceOnTop);
		return this;
	}

	/**
	 * Sets the border size on the top.
	 *
	 * @param spaceOnTop - the space
	 */

	public void setSpaceOnTop(int spaceOnTop) {
		this.spaceOnTop = spaceOnTop;
	}

}

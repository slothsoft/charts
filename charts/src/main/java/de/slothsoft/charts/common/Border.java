package de.slothsoft.charts.common;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.Chart;
import de.slothsoft.charts.ChartPart;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;
import de.slothsoft.charts.RefreshListener;
import de.slothsoft.charts.internal.RefreshListeners;

/**
 * A border on a {@link Chart}. On default it's only empty space and not visible.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class Border implements ChartPart {

	RefreshListeners refreshListeners = new RefreshListeners(this);

	private int spaceOnTop = 10;
	private int spaceOnLeft = 5;
	private int spaceOnRight = 5;
	private int spaceOnBottom = 7;

	@Override
	public void paintOn(GraphicContext gc, PaintInstructions instructions) {
		// this border is only empty space so don't draw anything

	}
	@Override
	public Area snipNecessarySpace(Area existingArea) {
		return new Area().startX(existingArea.getStartX() + this.spaceOnLeft)
				.startY(existingArea.getStartY() + this.spaceOnTop).endX(existingArea.getEndX() - this.spaceOnRight)
				.endY(existingArea.getEndY() - this.spaceOnBottom);
	}

	@Override
	public void addRefreshListener(RefreshListener listener) {
		this.refreshListeners.addRefreshListener(listener);
	}

	@Override
	public void removeRefreshListener(RefreshListener listener) {
		this.refreshListeners.removeRefreshListener(listener);
	}

	/**
	 * Sets the border size in all four directions.
	 *
	 * @param newSpace the space
	 * @return this instance
	 */

	public Border space(int newSpace) {
		setSpace(newSpace);
		return this;
	}

	/**
	 * Sets the border size in all four directions.
	 *
	 * @param space the space
	 */

	public void setSpace(int space) {
		final int oldSpace = hashCode(this.spaceOnBottom, this.spaceOnTop, this.spaceOnRight, this.spaceOnLeft);
		this.spaceOnBottom = space;
		this.spaceOnTop = space;
		this.spaceOnRight = space;
		this.spaceOnLeft = space;
		final int newSpace = hashCode(this.spaceOnBottom, this.spaceOnTop, this.spaceOnRight, this.spaceOnLeft);
		if (oldSpace != newSpace) {
			this.refreshListeners.fireRefreshNeeded();
		}
	}

	private static int hashCode(int... a) {
		int result = 1;
		for (final long element : a) {
			final int elementHash = (int) (element ^ (element >>> 32));
			result = 31 * result + elementHash;
		}
		return result;
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
	 * @param newSpaceOnBottom the space
	 * @return this instance
	 */

	public Border spaceOnBottom(int newSpaceOnBottom) {
		setSpaceOnBottom(newSpaceOnBottom);
		return this;
	}

	/**
	 * Sets the border size on the bottom.
	 *
	 * @param spaceOnBottom the space
	 */

	public void setSpaceOnBottom(int spaceOnBottom) {
		final int oldSpaceOnBottom = this.spaceOnBottom;
		this.spaceOnBottom = spaceOnBottom;
		if (oldSpaceOnBottom != this.spaceOnBottom) {
			this.refreshListeners.fireRefreshNeeded();
		}
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
	 * @param newSpaceOnLeft the space
	 * @return this instance
	 */

	public Border spaceOnLeft(int newSpaceOnLeft) {
		setSpaceOnLeft(newSpaceOnLeft);
		return this;
	}

	/**
	 * Sets the border size on the left.
	 *
	 * @param spaceOnLeft the space
	 */

	public void setSpaceOnLeft(int spaceOnLeft) {
		final int oldSpaceOnLeft = this.spaceOnLeft;
		this.spaceOnLeft = spaceOnLeft;
		if (oldSpaceOnLeft != this.spaceOnLeft) {
			this.refreshListeners.fireRefreshNeeded();
		}
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
	 * @param newSpaceOnRight the space
	 * @return this instance
	 */

	public Border spaceOnRight(int newSpaceOnRight) {
		setSpaceOnRight(newSpaceOnRight);
		return this;
	}

	/**
	 * Sets the border size on the right.
	 *
	 * @param spaceOnRight the space
	 */

	public void setSpaceOnRight(int spaceOnRight) {
		final int oldSpaceOnRight = this.spaceOnRight;
		this.spaceOnRight = spaceOnRight;
		if (oldSpaceOnRight != this.spaceOnRight) {
			this.refreshListeners.fireRefreshNeeded();
		}
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
	 * @param newSpaceOnTop the space
	 * @return this instance
	 */

	public Border spaceOnTop(int newSpaceOnTop) {
		setSpaceOnTop(newSpaceOnTop);
		return this;
	}

	/**
	 * Sets the border size on the top.
	 *
	 * @param spaceOnTop the space
	 */

	public void setSpaceOnTop(int spaceOnTop) {
		final int oldSpaceOnTop = this.spaceOnTop;
		this.spaceOnTop = spaceOnTop;
		if (oldSpaceOnTop != this.spaceOnTop) {
			this.refreshListeners.fireRefreshNeeded();
		}
	}

}

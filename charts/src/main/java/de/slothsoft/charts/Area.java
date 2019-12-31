package de.slothsoft.charts;

/**
 * A simple class representing an area with start and end point. Nothing special here.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class Area {

	double startX;
	double startY;
	double endX;
	double endY;

	/**
	 * Constructor that sets start and end points to 0|0.
	 */

	public Area() {
		this(0, 0);
	}

	/**
	 * Constructor that sets start point to 0|0.
	 *
	 * @param endX end point's X
	 * @param endY end point's Y
	 */

	public Area(double endX, double endY) {
		this(0, 0, endX, endY);
	}

	/**
	 * Constructor.
	 *
	 * @param startX start point's X
	 * @param startY start point's Y
	 * @param endX end point's X
	 * @param endY end point's Y
	 */

	public Area(double startX, double startY, double endX, double endY) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}

	/**
	 * Calculates this areas width from {@link #getStartX()} and {@link #getEndX()}.
	 *
	 * @return the width
	 * @since 0.2.0
	 */

	public double calculateWidth() {
		return this.endX - this.startX;
	}

	/**
	 * Calculates this areas height from {@link #getStartY()} and {@link #getEndY()}.
	 *
	 * @return the width
	 * @since 0.2.0
	 */

	public double calculateHeight() {
		return this.endY - this.startY;
	}

	/**
	 * Returns an area surrounding this area and the one coming in.
	 *
	 * @param area the area to unite with
	 * @return the union area
	 */

	public Area unite(Area area) {
		final double minX = Math.min(Math.min(this.startX, this.endX), Math.min(area.startX, area.endX));
		final double maxX = Math.max(Math.max(this.startX, this.endX), Math.max(area.startX, area.endX));
		final double minY = Math.min(Math.min(this.startY, this.endY), Math.min(area.startY, area.endY));
		final double maxY = Math.max(Math.max(this.startY, this.endY), Math.max(area.startY, area.endY));
		return new Area(minX, minY, maxX, maxY);
	}

	/**
	 * Returns true, if this area contains the point.
	 *
	 * @param x point's x
	 * @param y point's y
	 * @return true or false
	 */

	public boolean containsPoint(double x, double y) {
		return this.startX <= x && x <= this.endX && this.startY <= y && y <= this.endY;
	}

	/**
	 * Moves the area.
	 *
	 * @param xIncrement the x movement
	 * @param yIncrement the y movement
	 */

	public void move(double xIncrement, double yIncrement) {
		this.startX += xIncrement;
		this.startY += yIncrement;
		this.endX += xIncrement;
		this.endY += yIncrement;
	}

	public double getEndX() {
		return this.endX;
	}

	public Area endX(double newEndX) {
		setEndX(newEndX);
		return this;
	}

	public void setEndX(double endX) {
		this.endX = endX;
	}

	public double getEndY() {
		return this.endY;
	}

	public Area endY(double newEndY) {
		setEndY(newEndY);
		return this;
	}

	public void setEndY(double endY) {
		this.endY = endY;
	}

	public double getStartX() {
		return this.startX;
	}

	public Area startX(double newStartX) {
		setStartX(newStartX);
		return this;
	}

	public void setStartX(double startX) {
		this.startX = startX;
	}

	public double getStartY() {
		return this.startY;
	}

	public Area startY(double newStartY) {
		setStartY(newStartY);
		return this;
	}

	public void setStartY(double startY) {
		this.startY = startY;
	}

	@Override
	public String toString() {
		return "Area [startX=" + this.startX + ", startY=" + this.startY + ", endX=" + this.endX + ", endY=" + this.endY
				+ "]";
	}

	/**
	 * Creates a copy of this class.
	 *
	 * @return an equal copy
	 */

	public Area copy() {
		return new Area(this.startX, this.startY, this.endX, this.endY);
	}

	@Override
	public int hashCode() {
		return (int) ((((this.startX * 3 + this.startY) * 5 + this.endX) * 7 + this.endY) * 13);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final Area that = (Area) obj;
		if (this.startX != that.startX) return false;
		if (this.startY != that.startY) return false;
		if (this.endX != that.endX) return false;
		if (this.endY != that.endY) return false;
		return true;
	}

}

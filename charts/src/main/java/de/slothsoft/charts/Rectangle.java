package de.slothsoft.charts;

/**
 * A simple class representing a rectangle. Nothing special here.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class Rectangle {

	double x;
	double y;
	double width;
	double height;

	public Rectangle() {
		this(0, 0);
	}

	public Rectangle(double width, double height) {
		this(0, 0, width, height);
	}

	public Rectangle(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public double getHeight() {
		return this.height;
	}

	public Rectangle height(double newHeight) {
		setHeight(newHeight);
		return this;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return this.width;
	}

	public Rectangle width(double newWidth) {
		setWidth(newWidth);
		return this;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getX() {
		return this.x;
	}

	public Rectangle x(double newX) {
		setX(newX);
		return this;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return this.y;
	}

	public Rectangle y(double newY) {
		setY(newY);
		return this;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Rectangle [x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + "]";
	}

	/**
	 * Creates a copy of this class.
	 *
	 * @return an equal copy
	 */

	public Rectangle copy() {
		return new Rectangle(this.x, this.y, this.width, this.height);
	}

	@Override
	public int hashCode() {
		return (int) ((((this.x * 3 + this.y) * 5 + this.width) * 7 + this.height) * 13);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final Rectangle that = (Rectangle) obj;
		if (this.x != that.x) return false;
		if (this.y != that.y) return false;
		if (this.width != that.width) return false;
		if (this.height != that.height) return false;
		return true;
	}

}

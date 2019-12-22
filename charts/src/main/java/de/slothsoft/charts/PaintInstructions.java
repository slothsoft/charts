package de.slothsoft.charts;

import java.util.Objects;

/**
 * Additional instructions for a {@link Chart} or {@link Drawable} on what to paint
 * .Classes are connected like this: <br>
 * <img src="https://raw.githubusercontent.com/wiki/slothsoft/charts/images/UML.png" alt=
 * "UML Diagram">
 */

public class PaintInstructions {

	Area area;

	/**
	 * Default constructor. Only the area is really needed.
	 *
	 * @param area - the area to draw on; cannot be null
	 */

	public PaintInstructions(Area area) {
		this.area = Objects.requireNonNull(area);
	}

	/**
	 * Returns the area to draw on.
	 *
	 * @return the area to draw on; never null
	 */

	public Area getArea() {
		return this.area;
	}

	/**
	 * Sets the area to draw on.
	 *
	 * @param newArea - the area to draw on; cannot be null
	 * @return this instance
	 */

	public PaintInstructions area(Area newArea) {
		setArea(newArea);
		return this;
	}

	/**
	 * Sets the area to draw on.
	 *
	 * @param area - the area to draw on; cannot be null
	 */

	public void setArea(Area area) {
		this.area = Objects.requireNonNull(area);
	}

	// if you add any parameter, you need to change all these methods (potentially):

	@Override
	public String toString() {
		return "PaintInstructions [area=" + this.area + "]";
	}

	/**
	 * Creates a copy of this class.
	 *
	 * @return an equal copy
	 */

	public PaintInstructions copy() {
		return new PaintInstructions(this.area.copy());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.area);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final PaintInstructions that = (PaintInstructions) obj;
		if (!Objects.equals(this.area, that.area)) return false;
		return true;
	}

}

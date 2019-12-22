package de.slothsoft.charts.linechart;

import java.util.Objects;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;

/**
 * A {@link Line} that is based on a function.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class FunctionLine extends Line {

	/**
	 * The function a {@link FunctionLine} is based on.
	 *
	 * @author Stef Schulz
	 * @since 0.1.0
	 */

	public static interface Function {

		/**
		 * Applies this function to the given argument.
		 *
		 * @param x the function argument
		 * @return the function result (y)
		 */

		double apply(double x);
	}

	final Function function;
	private Area preferredArea;

	/**
	 * Constructor.
	 *
	 * @param function - the function; cannot be null
	 */

	public FunctionLine(Function function) {
		this.function = Objects.requireNonNull(function);
		this.preferredArea = calculateInitialPreferredArea();
	}

	private Area calculateInitialPreferredArea() {
		double startY = this.function.apply(DEFAULT_START_X);
		double endY = startY;

		for (int x = DEFAULT_START_X + 1; x <= DEFAULT_END_X; x++) {
			final double yValue = this.function.apply(x);
			startY = Math.min(yValue, startY);
			endY = Math.max(yValue, endY);
		}
		return new Area().startX(DEFAULT_START_X).startY(Math.min(0, startY)).endX(DEFAULT_END_X)
				.endY(Math.max(0, endY));
	}

	@Override
	protected Area calculatePreferredArea() {
		return this.preferredArea;
	}

	@Override
	public void paintOn(GraphicContext gc, PaintInstructions instructions) {
		// TODO: make startX, endX and step size customizable
		final Area drawnArea = instructions.getArea();
		final double startX = drawnArea.getStartX();
		final double endX = drawnArea.getEndY();
		final double step = 0.5;

		final int xLength = (int) ((endX - startX) / step);
		final double[] x = new double[xLength];
		final double[] y = new double[xLength];
		for (int i = 0; i < xLength; i++) {
			x[i] = startX + i * step;
			y[i] = this.function.apply(x[i]);
		}
		gc.setColor(this.color);
		gc.drawPolygon(x, y);
	}

	/**
	 * Returns the area this line would like to display. The line chart still has to merge
	 * this area with the preferred area of other {@link Line}s.
	 *
	 * @return the preferred area; never null
	 */

	public Area getPreferredArea() {
		return this.preferredArea;
	}

	/**
	 * Sets the area this line would like to display. The line chart still has to merge
	 * this area with the preferred area of other {@link Line}s.
	 *
	 * @param newPreferredArea - the preferred area; cannot be null
	 * @return this instance
	 */

	public FunctionLine preferredArea(Area newPreferredArea) {
		setPreferredArea(newPreferredArea);
		return this;
	}

	/**
	 * Sets the area this line would like to display. The line chart still has to merge
	 * this area with the preferred area of other {@link Line}s.
	 *
	 * @param preferredArea - the preferred area; cannot be null
	 */

	public void setPreferredArea(Area preferredArea) {
		this.preferredArea = Objects.requireNonNull(preferredArea);
	}

}

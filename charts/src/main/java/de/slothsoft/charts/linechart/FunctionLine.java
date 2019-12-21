package de.slothsoft.charts.linechart;

import java.util.Objects;

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

	/**
	 * Constructor.
	 *
	 * @param function - the function; cannot be null
	 */

	public FunctionLine(Function function) {
		this.function = Objects.requireNonNull(function);
	}

	@Override
	public void paintOn(GraphicContext gc, PaintInstructions instructions) {
		// TODO: make startX, endX and step size customizable
		final int startX = 0;
		final int endX = 30;

		final int xLength = endX - startX;
		final double[] x = new double[xLength];
		final double[] y = new double[xLength];
		for (int i = 0; i < xLength; i++) {
			x[i] = i;
			y[i] = this.function.apply(i);
		}
		gc.setColor(this.color);
		gc.drawPolygon(x, y);
	}
}

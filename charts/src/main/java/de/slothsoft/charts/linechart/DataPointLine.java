package de.slothsoft.charts.linechart;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;

/**
 * A {@link Line} that is based on data points, i.e. x and y coordinates.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class DataPointLine extends Line {

	final double[] x;
	final double[] y;

	/**
	 * Constructor with only the y values. The x coordinates will be 0, 1, ..., [length of
	 * y].
	 *
	 * @param y - the values for y
	 */

	public DataPointLine(double... y) {
		this(IntStream.range(0, y.length).asDoubleStream().toArray(), y);
	}

	/**
	 * Constructor with x and y values. Both arrays need to have the same length.
	 *
	 * @param x - the values for x
	 * @param y - the values for y
	 */

	public DataPointLine(double[] x, double[] y) {
		if (x.length != y.length) {
			throw new IllegalArgumentException(
					"Arrays for x and y must have some length! (" + x.length + " != " + y.length + ")");
		}
		this.x = Objects.requireNonNull(x);
		this.y = Objects.requireNonNull(y);
	}

	@Override
	protected Area calculatePreferredArea() {
		final double minX = Arrays.stream(this.x).min().orElse(DEFAULT_START_X);
		final double maxX = Arrays.stream(this.x).max().orElse(DEFAULT_END_X);
		final double minY = Arrays.stream(this.y).min().orElse(DEFAULT_START_Y);
		final double maxY = Arrays.stream(this.y).max().orElse(DEFAULT_END_Y);
		return new Area(Math.min(minX, 0), Math.min(minY, 0), maxX, maxY);
	}

	@Override
	public void paintOn(GraphicContext gc, PaintInstructions instructions) {
		gc.setColor(this.color);
		gc.drawPolyline(this.x, this.y);
	}
}

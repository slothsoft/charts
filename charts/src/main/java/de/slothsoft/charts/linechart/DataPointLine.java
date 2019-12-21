package de.slothsoft.charts.linechart;

import java.awt.Color;
import java.util.Objects;
import java.util.stream.IntStream;

import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;
import de.slothsoft.charts.Rectangle;

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
		if (x.length != y.length)
			throw new IllegalArgumentException(
					"Arrays for x and y must have some length! (" + x.length + " != " + y.length + ")");
		this.x = Objects.requireNonNull(x);
		this.y = Objects.requireNonNull(y);
	}

	@Override
	public void paintOn(GraphicContext gc, PaintInstructions instructions) {
		final Rectangle rect = instructions.getArea();
		gc.setColor(Color.red.getRGB());
		gc.drawLine(0, 0, rect.getWidth(), 0);
		gc.setColor(Color.blue.getRGB());
		gc.drawLine(0, 0, 0, rect.getHeight());
		gc.setColor(this.color);
		gc.drawPolygon(this.x, this.y);
	}
}

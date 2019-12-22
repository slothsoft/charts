package de.slothsoft.charts.linechart;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.charts.Area;

public class FunctionLineTest {

	private static final double DELTA = 0.001;

	@Test
	public void testInitialPreferredAreaYGreaterThanZero() throws Exception {
		final FunctionLine line = new FunctionLine(x -> 5);

		Assert.assertEquals(new Area(Line.DEFAULT_START_X, Line.DEFAULT_START_Y, Line.DEFAULT_END_X, 5),
				line.getPreferredArea());
	}

	@Test
	public void testInitialPreferredAreaYLessThanZero() throws Exception {
		final FunctionLine line = new FunctionLine(x -> -5);

		Assert.assertEquals(new Area(Line.DEFAULT_START_X, -5, Line.DEFAULT_END_X, 0), line.getPreferredArea());
	}

	@Test
	public void testInitialPreferredAreaYIsEverywhere() throws Exception {
		final int minus = 5;
		final FunctionLine line = new FunctionLine(x -> x - minus);

		Assert.assertEquals(new Area(Line.DEFAULT_START_X, Line.DEFAULT_START_X - minus, Line.DEFAULT_END_X,
				Line.DEFAULT_END_X - minus), line.calculatePreferredArea());
	}

}

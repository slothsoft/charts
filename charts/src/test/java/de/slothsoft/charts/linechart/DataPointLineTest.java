package de.slothsoft.charts.linechart;

import org.junit.Assert;
import org.junit.Test;

public class DataPointLineTest {

	private static final double DELTA = 0.001;

	@Test
	public void testConstructor() throws Exception {
		final DataPointLine line = new DataPointLine(4, 5, 6);

		Assert.assertArrayEquals(new double[]{0, 1, 2}, line.x, DELTA);
		Assert.assertArrayEquals(new double[]{4, 5, 6}, line.y, DELTA);
	}

}

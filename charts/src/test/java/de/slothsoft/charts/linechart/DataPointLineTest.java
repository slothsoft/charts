package de.slothsoft.charts.linechart;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.charts.Area;

public class DataPointLineTest {

	private static final double DELTA = 0.001;

	@Test
	public void testConstructor() throws Exception {
		final DataPointLine line = new DataPointLine(4, 5, 6);

		Assert.assertArrayEquals(new double[]{0, 1, 2}, line.x, DELTA);
		Assert.assertArrayEquals(new double[]{4, 5, 6}, line.y, DELTA);
	}

	@Test
	public void testCalculatePreferredAreaXStartingWith0() throws Exception {
		final DataPointLine line = new DataPointLine(4, 5, 6);

		Assert.assertEquals(new Area(2, 6), line.calculatePreferredArea());
	}

	@Test
	public void testCalculatePreferredAreaYStartingWith0() throws Exception {
		final DataPointLine line = new DataPointLine(new double[]{1, 2, 3}, new double[]{0, 3, 6});

		Assert.assertEquals(new Area(3, 6), line.calculatePreferredArea());
	}

	@Test
	public void testCalculatePreferredAreaXIsNegative() throws Exception {
		final DataPointLine line = new DataPointLine(new double[]{-1, 0, 1}, new double[]{4, 5, 6});

		Assert.assertEquals(new Area(-1, 0, 1, 6), line.calculatePreferredArea());
	}

	@Test
	public void testCalculatePreferredAreaYIsNegative() throws Exception {
		final DataPointLine line = new DataPointLine(new double[]{1, 2, 3}, new double[]{-1, 0, 1});

		Assert.assertEquals(new Area(0, -1, 3, 1), line.calculatePreferredArea());
	}

	@Test
	public void testCalculatePreferredAreaXAndYIsNegative() throws Exception {
		final DataPointLine line = new DataPointLine(new double[]{-2, 0, 2}, new double[]{-1, 0, 1});

		Assert.assertEquals(new Area(-2, -1, 2, 1), line.calculatePreferredArea());
	}

	@Test
	public void testCalculatePreferredAreaEmpty() throws Exception {
		final DataPointLine line = new DataPointLine();

		Assert.assertEquals(Line.createDefaultArea(), line.calculatePreferredArea());
	}

}

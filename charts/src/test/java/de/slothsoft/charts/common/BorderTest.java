package de.slothsoft.charts.common;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.charts.Area;

public class BorderTest {

	private static final double DELTA = 0.001;

	private final Border border = new Border();

	@Test
	public void testSnipNecessarySpace() throws Exception {
		this.border.spaceOnTop(1).spaceOnLeft(2).spaceOnRight(3).spaceOnBottom(4);
		final Area result = this.border.snipNecessarySpace(new Area(10, 20, 30, 40));

		Assert.assertEquals(12, result.getStartX(), DELTA);
		Assert.assertEquals(21, result.getStartY(), DELTA);
		Assert.assertEquals(27, result.getEndX(), DELTA);
		Assert.assertEquals(36, result.getEndY(), DELTA);
	}
}

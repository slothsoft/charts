package de.slothsoft.charts.common;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.charts.Rectangle;

public class BorderTest {

	private static final double DELTA = 0.001;

	private final Border border = new Border();

	@Test
	public void testSnipNecessarySpace() throws Exception {
		this.border.spaceOnTop(1).spaceOnLeft(2).spaceOnRight(3).spaceOnBottom(4);
		final Rectangle result = this.border.snipNecessarySpace(new Rectangle(10, 20, 30, 40));

		Assert.assertEquals(12, result.getX(), DELTA);
		Assert.assertEquals(21, result.getY(), DELTA);
		Assert.assertEquals(25, result.getWidth(), DELTA);
		Assert.assertEquals(35, result.getHeight(), DELTA);
	}
}

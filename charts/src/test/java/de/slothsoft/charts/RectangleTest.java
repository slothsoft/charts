package de.slothsoft.charts;

import org.junit.Test;

import de.slothsoft.charts.Rectangle;

public class RectangleTest extends BeanTest {

	@Test
	public void testEqual() {
		assertEquals(new Rectangle(), new Rectangle());
		assertEquals(new Rectangle(), new Rectangle(0, 0));
		assertEquals(new Rectangle(1, 2), new Rectangle(0, 0, 1, 2));
		assertEquals(new Rectangle(1, 2, 3, 4), new Rectangle(1, 2, 3, 4));
	}

	@Test
	public void testNotEqual() {
		assertNotEquals(new Rectangle(1, 2, 3, 4), new Rectangle(5, 2, 3, 4));
		assertNotEquals(new Rectangle(1, 2, 3, 4), new Rectangle(1, 5, 3, 4));
		assertNotEquals(new Rectangle(1, 2, 3, 4), new Rectangle(1, 2, 5, 4));
		assertNotEquals(new Rectangle(1, 2, 3, 4), new Rectangle(1, 2, 3, 5));
	}

	@Test
	public void testCopy() {
		final Rectangle original = new Rectangle(1, 2, 3, 4);
		assertEquals(original, original.copy());
	}

}

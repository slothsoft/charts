package de.slothsoft.charts;

import org.junit.Test;

import de.slothsoft.charts.Area;

public class RectangleTest extends BeanTest {

	@Test
	public void testEqual() {
		assertEquals(new Area(), new Area());
		assertEquals(new Area(), new Area(0, 0));
		assertEquals(new Area(1, 2), new Area(0, 0, 1, 2));
		assertEquals(new Area(1, 2, 3, 4), new Area(1, 2, 3, 4));
	}

	@Test
	public void testNotEqual() {
		assertNotEquals(new Area(1, 2, 3, 4), new Area(5, 2, 3, 4));
		assertNotEquals(new Area(1, 2, 3, 4), new Area(1, 5, 3, 4));
		assertNotEquals(new Area(1, 2, 3, 4), new Area(1, 2, 5, 4));
		assertNotEquals(new Area(1, 2, 3, 4), new Area(1, 2, 3, 5));
	}

	@Test
	public void testCopy() {
		final Area original = new Area(1, 2, 3, 4);
		assertEquals(original, original.copy());
	}

}

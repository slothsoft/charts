package de.slothsoft.charts;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("static-method")
public class AreaTest {

	@Test
	public void testUnite() throws Exception {
		final Area area1 = new Area(1, 2, 3, 4);
		final Area area2 = new Area(6, 7, 8, 9);

		Assert.assertEquals(new Area(1, 2, 8, 9), area1.unite(area2));
		Assert.assertEquals(new Area(1, 2, 8, 9), area2.unite(area1));
	}

	@Test
	public void testUniteFlipPoints() throws Exception {
		final Area area1 = new Area(3, 4, 1, 2);
		final Area area2 = new Area(8, 9, 6, 7);

		Assert.assertEquals(new Area(1, 2, 8, 9), area1.unite(area2));
		Assert.assertEquals(new Area(1, 2, 8, 9), area2.unite(area1));
	}

	@Test
	public void testUniteIntersectingAreas() throws Exception {
		final Area area1 = new Area(1, 2, 8, 9);
		final Area area2 = new Area(6, 7, 3, 4);

		Assert.assertEquals(new Area(1, 2, 8, 9), area1.unite(area2));
		Assert.assertEquals(new Area(1, 2, 8, 9), area2.unite(area1));
	}

	@Test
	public void testUniteEqualAreas() throws Exception {
		final Area area1 = new Area(1, 2, 3, 4);
		final Area area2 = new Area(1, 2, 3, 4);

		Assert.assertEquals(new Area(1, 2, 3, 4), area1.unite(area2));
		Assert.assertEquals(new Area(1, 2, 3, 4), area2.unite(area1));
	}

	@Test
	public void testMove() throws Exception {
		final Area area = new Area(1, 2, 4, 7);

		area.move(-1, -2);
		Assert.assertEquals(new Area(0, 0, 3, 5), area);
	}

	@Test
	public void testContainsPoint() throws Exception {
		Assert.assertTrue(new Area().containsPoint(0, 0));
		Assert.assertTrue(new Area(1, 2).containsPoint(0.5, 1));
		Assert.assertTrue(new Area(1, 2, 3, 4).containsPoint(1, 2));
		Assert.assertTrue(new Area(1, 2, 3, 4).containsPoint(3, 4));
		Assert.assertTrue(new Area(1, 2, 3, 4).containsPoint(1, 4));
		Assert.assertTrue(new Area(1, 2, 3, 4).containsPoint(3, 2));
	}

	@Test
	public void testContainsPointNot() throws Exception {
		Assert.assertFalse(new Area().containsPoint(1, 2));
		Assert.assertFalse(new Area(1, 2).containsPoint(-1, 1));
		Assert.assertFalse(new Area(1, 2, 3, 4).containsPoint(0, 0));
	}
}

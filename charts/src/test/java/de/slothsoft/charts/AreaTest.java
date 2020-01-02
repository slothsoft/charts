package de.slothsoft.charts;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("static-method")
public class AreaTest {

	private static final double DELTA = 0.001;

	@Test
	public void testEqual() {
		final Area area = new Area();
		Assert.assertEquals(area, area);
		Assert.assertEquals(new Area(), new Area());
		Assert.assertEquals(new Area(1, 2), new Area(1, 2));
		Assert.assertEquals(new Area(1, 2, 3, 4), new Area(1, 2, 3, 4));
	}

	@Test
	public void testNotEqual() {
		Assert.assertNotEquals(new Area(1, 2), new Area(2, 2));
		Assert.assertNotEquals(new Area(1, 2), new Area(1, 3));
		Assert.assertNotEquals(new Area(1, 2, 3, 4), new Area(2, 2, 3, 4));
		Assert.assertNotEquals(new Area(1, 2, 3, 4), new Area(1, 3, 3, 4));
		Assert.assertNotEquals(new Area(1, 2, 3, 4), new Area(1, 2, 2, 4));
		Assert.assertNotEquals(new Area(1, 2, 3, 4), new Area(1, 2, 3, 3));
	}

	@Test
	public void testEqualSpecialCases() {
		Assert.assertFalse(new Area().equals(null));
		Assert.assertFalse(new Area().equals(new Object()));
	}

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

	@Test
	public void testCalculateWidth() throws Exception {
		Assert.assertEquals(0, new Area().calculateWidth(), DELTA);
		Assert.assertEquals(1, new Area(1, 2).calculateWidth(), DELTA);
		Assert.assertEquals(3, new Area(1, 2, 4, 6).calculateWidth(), DELTA);
	}

	@Test
	public void testCalculateHeight() throws Exception {
		Assert.assertEquals(0, new Area().calculateHeight(), DELTA);
		Assert.assertEquals(2, new Area(1, 2).calculateHeight(), DELTA);
		Assert.assertEquals(4, new Area(1, 2, 4, 6).calculateHeight(), DELTA);
	}
}

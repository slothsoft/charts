package de.slothsoft.charts.common;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.RefreshListener;

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

	@Test
	public void testSetSpaceOnTop() throws Exception {
		this.border.spaceOnTop(1);
		Assert.assertEquals(1, this.border.getSpaceOnTop(), DELTA);

		this.border.setSpaceOnTop(2);
		Assert.assertEquals(2, this.border.getSpaceOnTop(), DELTA);
	}

	@Test
	public void testSetSpaceOnBottom() throws Exception {
		this.border.spaceOnBottom(1);
		Assert.assertEquals(1, this.border.getSpaceOnBottom(), DELTA);

		this.border.setSpaceOnBottom(2);
		Assert.assertEquals(2, this.border.getSpaceOnBottom(), DELTA);
	}

	@Test
	public void testSetSpaceOnRight() throws Exception {
		this.border.spaceOnRight(1);
		Assert.assertEquals(1, this.border.getSpaceOnRight(), DELTA);

		this.border.setSpaceOnRight(2);
		Assert.assertEquals(2, this.border.getSpaceOnRight(), DELTA);
	}

	@Test
	public void testSetSpaceOnLeft() throws Exception {
		this.border.spaceOnLeft(1);
		Assert.assertEquals(1, this.border.getSpaceOnLeft(), DELTA);

		this.border.setSpaceOnLeft(2);
		Assert.assertEquals(2, this.border.getSpaceOnLeft(), DELTA);
	}

	@Test
	public void testSetSpace() throws Exception {
		this.border.space(1);
		Assert.assertEquals(1, this.border.getSpaceOnTop(), DELTA);
		Assert.assertEquals(1, this.border.getSpaceOnBottom(), DELTA);
		Assert.assertEquals(1, this.border.getSpaceOnRight(), DELTA);
		Assert.assertEquals(1, this.border.getSpaceOnLeft(), DELTA);

		this.border.setSpace(2);
		Assert.assertEquals(2, this.border.getSpaceOnTop(), DELTA);
		Assert.assertEquals(2, this.border.getSpaceOnBottom(), DELTA);
		Assert.assertEquals(2, this.border.getSpaceOnRight(), DELTA);
		Assert.assertEquals(2, this.border.getSpaceOnLeft(), DELTA);
	}

	@Test
	public void testAddRefreshListener() throws Exception {
		final RefreshListener.Event[] called = {null};
		this.border.addRefreshListener(e -> called[0] = e);
		this.border.setSpaceOnLeft(100);

		Assert.assertNotNull(called[0]);
		Assert.assertNotNull(called[0].getSource());
	}

	@Test
	public void testRemoveRefreshListener() throws Exception {
		final RefreshListener.Event[] called = {null};
		final RefreshListener listener = e -> called[0] = e;
		this.border.addRefreshListener(listener);
		this.border.removeRefreshListener(listener);
		this.border.setSpaceOnLeft(100);

		Assert.assertNull(called[0]);
	}
}

package de.slothsoft.charts.linechart;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;
import de.slothsoft.charts.RefreshListener;

public class AxisTest {

	private static final double DELTA = 0.001;

	private final Axis axis = new Axis() {

		@Override
		public void paintOn(GraphicContext gc, PaintInstructions instructions) {
			// nothing to do in test
		}
	};

	@Test
	public void testSetArrowSize() throws Exception {
		this.axis.setArrowSize(1);
		Assert.assertEquals(1, this.axis.getArrowSize(), DELTA);

		this.axis.arrowSize(10);
		Assert.assertEquals(10, this.axis.getArrowSize(), DELTA);
	}

	@Test
	public void testSetTickSize() throws Exception {
		this.axis.setTickSize(2);
		Assert.assertEquals(2, this.axis.getTickSize(), DELTA);

		this.axis.tickSize(20);
		Assert.assertEquals(20, this.axis.getTickSize(), DELTA);
	}

	@Test
	public void testSetBigTickSize() throws Exception {
		this.axis.setBigTickSize(3);
		Assert.assertEquals(3, this.axis.getBigTickSize(), DELTA);

		this.axis.bigTickSize(30);
		Assert.assertEquals(30, this.axis.getBigTickSize(), DELTA);
	}

	@Test
	public void testSetTickSteps() throws Exception {
		this.axis.setTickSteps(4);
		Assert.assertEquals(4, this.axis.getTickSteps(), DELTA);

		this.axis.tickSteps(40);
		Assert.assertEquals(40, this.axis.getTickSteps(), DELTA);
	}

	@Test
	public void testSetBigTickSteps() throws Exception {
		this.axis.setBigTickSteps(5);
		Assert.assertEquals(5, this.axis.getBigTickSteps(), DELTA);

		this.axis.bigTickSteps(50);
		Assert.assertEquals(50, this.axis.getBigTickSteps(), DELTA);
	}

	@Test
	public void testAddRefreshListener() throws Exception {
		final RefreshListener.Event[] called = {null};
		this.axis.addRefreshListener(e -> called[0] = e);
		this.axis.setArrowSize(99);

		Assert.assertNotNull(called[0]);
		Assert.assertNotNull(called[0].getSource());
	}

	@Test
	public void testRemoveRefreshListener() throws Exception {
		final RefreshListener.Event[] called = {null};
		final RefreshListener listener = e -> called[0] = e;
		this.axis.addRefreshListener(listener);
		this.axis.removeRefreshListener(listener);
		this.axis.setArrowSize(99);

		Assert.assertNull(called[0]);
	}
}

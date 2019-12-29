package de.slothsoft.charts.piechart;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.charts.AbstractChartTest;
import de.slothsoft.charts.RefreshListener;

public class PieChartTest extends AbstractChartTest<PieChart> {

	private static final double DELTA = 0.001;

	@Override
	protected PieChart createChart() {
		return new PieChart();
	}

	@Test
	public void testAddSlice() throws Exception {
		final PieSlice slice = this.chart.addSlice(1);

		Assert.assertEquals(1, this.chart.slices.size());
		Assert.assertTrue(this.chart.slices.contains(slice));
	}

	@Test
	public void testRemoveSlice() throws Exception {
		final PieSlice slice = this.chart.addSlice(2);
		this.chart.removeSlice(slice);

		Assert.assertFalse(this.chart.slices.contains(slice));
	}

	@Test
	public void testAddSlices() throws Exception {
		final PieSlice[] slices = this.chart.addSlices(1, 2);

		Assert.assertEquals(2, this.chart.slices.size());
		Assert.assertTrue(this.chart.slices.contains(slices[0]));
		Assert.assertTrue(this.chart.slices.contains(slices[1]));
	}

	@Test
	public void testRemoveSlices() throws Exception {
		final PieSlice[] slices = this.chart.addSlices(3, 4);
		this.chart.removeSlices(slices);

		Assert.assertFalse(this.chart.slices.contains(slices[0]));
		Assert.assertFalse(this.chart.slices.contains(slices[1]));
	}

	@Test
	public void testAddSliceRefreshWasExecuted() throws Exception {
		final PieSlice slice = this.chart.addSlice(7);

		final RefreshListener.Event[] called = {null};
		this.chart.addRefreshListener(e -> called[0] = e);
		slice.setColor(0xFFEDCBA0);

		Assert.assertNotNull(called[0]);
		Assert.assertNotNull(called[0].getSource());
	}

	@Test
	public void testAddSliceRefreshWasNotExecutedAfterRemove() throws Exception {
		final PieSlice slice = this.chart.addSlice(42);
		this.chart.removeSlice(slice);

		final RefreshListener.Event[] called = {null};
		final RefreshListener listener = e -> called[0] = e;
		this.chart.addRefreshListener(listener);
		slice.setColor(0xFFEDCBA0);

		Assert.assertNull(called[0]);
	}

	@Test
	public void testAddSliceRefreshWasNotExecutedByTwoCalls() throws Exception {
		final PieSlice slice = this.chart.addSlice(6);
		slice.setColor(0xFFEDCBA0);

		final RefreshListener.Event[] called = {null};
		final RefreshListener listener = e -> called[0] = e;
		this.chart.addRefreshListener(listener);
		slice.setColor(0xFFEDCBA0);

		Assert.assertNull(called[0]);
	}

	@Test
	public void testAddSlicesRefreshWasExecuted() throws Exception {
		final PieSlice[] slices = this.chart.addSlices(4);

		final RefreshListener.Event[] called = {null};
		this.chart.addRefreshListener(e -> called[0] = e);
		slices[0].setColor(0xFFEDCBA0);

		Assert.assertNotNull(called[0]);
		Assert.assertNotNull(called[0].getSource());
	}

	@Test
	public void testAddSlicesRefreshWasNotExecutedAfterRemove() throws Exception {
		final PieSlice[] slices = this.chart.addSlices(4, 6);
		this.chart.removeSlices(slices);

		final RefreshListener.Event[] called = {null};
		final RefreshListener listener = e -> called[0] = e;
		this.chart.addRefreshListener(listener);
		slices[0].setColor(0xFFEDCBA0);

		Assert.assertNull(called[0]);
	}

	@Test
	public void testAddSlicesRefreshWasNotExecutedByTwoCalls() throws Exception {
		final PieSlice[] slices = this.chart.addSlices(1);
		slices[0].setColor(0xFFEDCBA0);

		final RefreshListener.Event[] called = {null};
		final RefreshListener listener = e -> called[0] = e;
		this.chart.addRefreshListener(listener);
		slices[0].setColor(0xFFEDCBA0);

		Assert.assertNull(called[0]);
	}

	@Test
	public void testSetPieColor() throws Exception {
		this.chart.pieColor(0xFFABCDEF);
		Assert.assertEquals(0xFFABCDEF, this.chart.getPieColor());

		this.chart.setPieColor(0xFFFEDCBA);
		Assert.assertEquals(0xFFFEDCBA, this.chart.getPieColor());
	}

	@Test
	public void testSetPieBorder() throws Exception {
		this.chart.pieBorder(1);
		Assert.assertEquals(1, this.chart.getPieBorder(), DELTA);

		this.chart.setPieBorder(2);
		Assert.assertEquals(2, this.chart.getPieBorder(), DELTA);
	}

	@Test
	public void testSetStartAngle() throws Exception {
		this.chart.startAngle(1);
		Assert.assertEquals(1, this.chart.getStartAngle(), DELTA);

		this.chart.setStartAngle(2);
		Assert.assertEquals(2, this.chart.getStartAngle(), DELTA);
	}
}

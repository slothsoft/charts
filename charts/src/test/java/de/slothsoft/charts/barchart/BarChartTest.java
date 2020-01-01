package de.slothsoft.charts.barchart;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.charts.AbstractChartTest;
import de.slothsoft.charts.RefreshListener;

public class BarChartTest extends AbstractChartTest<BarChart> {

	private static final double DELTA = 0.001;

	@Override
	protected BarChart createChart() {
		return new BarChart();
	}

	@Test
	public void testAdd() throws Exception {
		final Bar bar = this.chart.addBar(1);

		Assert.assertEquals(1, this.chart.bars.size());
		Assert.assertTrue(this.chart.bars.contains(bar));
	}

	@Test
	public void testRemove() throws Exception {
		final Bar bar = this.chart.addBar(2);
		this.chart.removeBar(bar);

		Assert.assertFalse(this.chart.bars.contains(bar));
	}

	@Test
	public void testAdds() throws Exception {
		final Bar[] bars = this.chart.addBars(1, 2);

		Assert.assertEquals(2, this.chart.bars.size());
		Assert.assertTrue(this.chart.bars.contains(bars[0]));
		Assert.assertTrue(this.chart.bars.contains(bars[1]));
	}

	@Test
	public void testRemoves() throws Exception {
		final Bar[] bars = this.chart.addBars(3, 4);
		this.chart.removeBars(bars);

		Assert.assertFalse(this.chart.bars.contains(bars[0]));
		Assert.assertFalse(this.chart.bars.contains(bars[1]));
	}

	@Test
	public void testAddRefreshWasExecuted() throws Exception {
		final Bar bar = this.chart.addBar(7);

		final RefreshListener.Event[] called = {null};
		this.chart.addRefreshListener(e -> called[0] = e);
		bar.setColor(0xFFEDCBA0);

		Assert.assertNotNull(called[0]);
		Assert.assertNotNull(called[0].getSource());
	}

	@Test
	public void testAddRefreshWasNotExecutedAfterRemove() throws Exception {
		final Bar bar = this.chart.addBar(42);
		this.chart.removeBar(bar);

		final RefreshListener.Event[] called = {null};
		final RefreshListener listener = e -> called[0] = e;
		this.chart.addRefreshListener(listener);
		bar.setColor(0xFFEDCBA0);

		Assert.assertNull(called[0]);
	}

	@Test
	public void testAddRefreshWasNotExecutedByTwoCalls() throws Exception {
		final Bar bar = this.chart.addBar(6);
		bar.setColor(0xFFEDCBA0);

		final RefreshListener.Event[] called = {null};
		final RefreshListener listener = e -> called[0] = e;
		this.chart.addRefreshListener(listener);
		bar.setColor(0xFFEDCBA0);

		Assert.assertNull(called[0]);
	}

	@Test
	public void testAddsRefreshWasExecuted() throws Exception {
		final Bar[] bars = this.chart.addBars(4);

		final RefreshListener.Event[] called = {null};
		this.chart.addRefreshListener(e -> called[0] = e);
		bars[0].setColor(0xFFEDCBA0);

		Assert.assertNotNull(called[0]);
		Assert.assertNotNull(called[0].getSource());
	}

	@Test
	public void testAddsRefreshWasNotExecutedAfterRemove() throws Exception {
		final Bar[] bars = this.chart.addBars(4, 6);
		this.chart.removeBars(bars);

		final RefreshListener.Event[] called = {null};
		final RefreshListener listener = e -> called[0] = e;
		this.chart.addRefreshListener(listener);
		bars[0].setColor(0xFFEDCBA0);

		Assert.assertNull(called[0]);
	}

	@Test
	public void testAddsRefreshWasNotExecutedByTwoCalls() throws Exception {
		final Bar[] bars = this.chart.addBars(1);
		bars[0].setColor(0xFFEDCBA0);

		final RefreshListener.Event[] called = {null};
		final RefreshListener listener = e -> called[0] = e;
		this.chart.addRefreshListener(listener);
		bars[0].setColor(0xFFEDCBA0);

		Assert.assertNull(called[0]);
	}

	@Test
	public void testSetDefaultBarColor() throws Exception {
		this.chart.defaultBarColor(0xFFABCDEF);
		Assert.assertEquals(0xFFABCDEF, this.chart.getDefaultBarColor());

		this.chart.setDefaultBarColor(0xFFFEDCBA);
		Assert.assertEquals(0xFFFEDCBA, this.chart.getDefaultBarColor());
	}
}

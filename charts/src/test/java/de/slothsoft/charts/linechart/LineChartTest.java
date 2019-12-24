package de.slothsoft.charts.linechart;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.charts.AbstractChartTest;
import de.slothsoft.charts.Area;

public class LineChartTest extends AbstractChartTest<LineChart> {

	@Override
	protected LineChart createChart() {
		return new LineChart();
	}

	@Test
	public void testAddLine() throws Exception {
		final Line line = new DataPointLine(4, 5, 6);
		this.chart.addLine(line);

		Assert.assertTrue(this.chart.lines.contains(line));
		// see DataPointLineTesttestCalculatePreferredAreaXStartingWith0()
		Assert.assertEquals(new Area(2, 6), this.chart.calculateDisplayedArea());
	}

	@Test
	public void testRemoveLine() throws Exception {
		final Line line = new DataPointLine(4, 5, 6);
		this.chart.addLine(line);
		this.chart.removeLine(line);

		Assert.assertFalse(this.chart.lines.contains(line));
		Assert.assertEquals(new Area(10, 10), this.chart.calculateDisplayedArea());
	}

	@Test
	public void testAddLines() throws Exception {
		final Line line1 = new DataPointLine(4, 5, 6);
		final Line line2 = new DataPointLine(-3, -4, -5);
		this.chart.addLines(line1, line2);

		Assert.assertTrue(this.chart.lines.contains(line1));
		Assert.assertTrue(this.chart.lines.contains(line2));
		Assert.assertEquals(new Area(0, -5, 2, 6), this.chart.calculateDisplayedArea());
	}

	@Test
	public void testRemoveLines() throws Exception {
		final Line line1 = new DataPointLine(4, 5, 6);
		final Line line2 = new DataPointLine(-3, -4, -5);
		this.chart.addLines(line1, line2);
		this.chart.removeLines(line1, line2);

		Assert.assertFalse(this.chart.lines.contains(line1));
		Assert.assertFalse(this.chart.lines.contains(line2));
		Assert.assertEquals(new Area(10, 10), this.chart.calculateDisplayedArea());
	}

	@Test
	public void testCalculateDisplayedAreaOnDefault() throws Exception {
		Assert.assertEquals(new Area(10, 10), this.chart.calculateDisplayedArea());
	}

	@Test
	public void testCalculateDisplayedAreaOnSet() throws Exception {
		this.chart.setDisplayedArea(new Area(1, 2, 3, 4));

		Assert.assertEquals(new Area(1, 2, 3, 4), this.chart.calculateDisplayedArea());
	}

	@Test
	public void testMoveDisplayedAreaDefault() throws Exception {
		this.chart.setDisplayedArea(null);
		final Area defaultArea = this.chart.calculateDisplayedArea();

		this.chart.moveDisplayedArea(5, 6);
		Assert.assertEquals(new Area(defaultArea.getStartX() + 5, defaultArea.getStartY() + 6,
				defaultArea.getEndX() + 5, defaultArea.getEndY() + 6), this.chart.getDisplayedArea());
	}

	@Test
	public void testMoveDisplayedArea() throws Exception {
		this.chart.setDisplayedArea(new Area(1, 2, 4, 7));

		this.chart.moveDisplayedArea(-1, -2);
		Assert.assertEquals(new Area(0, 0, 3, 5), this.chart.getDisplayedArea());
	}
}

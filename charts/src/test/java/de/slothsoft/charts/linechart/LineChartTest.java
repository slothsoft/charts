package de.slothsoft.charts.linechart;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.charts.AbstractChartTest;
import de.slothsoft.charts.Area;

public class LineChartTest extends AbstractChartTest<LineChart> {

	private static final double DELTA = 0.001;

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

		this.chart.moveDisplayedAreaDirectly(5, 6);
		Assert.assertEquals(new Area(defaultArea.getStartX() + 5, defaultArea.getStartY() + 6,
				defaultArea.getEndX() + 5, defaultArea.getEndY() + 6), this.chart.getDisplayedArea());
	}

	@Test
	public void testMoveDisplayedAreaDirectly() throws Exception {
		this.chart.setDisplayedArea(new Area(1, 2, 4, 7));

		this.chart.moveDisplayedAreaDirectly(-1, -2);
		Assert.assertEquals(new Area(0, 0, 3, 5), this.chart.getDisplayedArea());
	}

	@Test
	public void testMoveDisplayedAreaByChartCoordinates() throws Exception {
		this.chart.setDisplayedArea(new Area(60, 60));
		this.chart.lastGraphArea = new Area(10, 20, 30, 50);

		this.chart.moveDisplayedAreaByChartCoordinates(6, 2);
		// last graph's width is 20, that's 1/3 of the display area, so move 6 * 3 -> 18
		// last graph's height is 30, that's 1/2 of the display area, so move 2 * 2 -> 4
		Assert.assertEquals(new Area(18, 4, 78, 64), this.chart.getDisplayedArea());
	}

	@Test
	public void testMoveDisplayedAreaByChartCoordinatesDefault() throws Exception {
		this.chart.setDisplayedArea(null);
		Assert.assertEquals(new Area(10, 10), this.chart.calculateDisplayedArea());
		this.chart.lastGraphArea = new Area(10, 20, 30, 50);

		this.chart.moveDisplayedAreaByChartCoordinates(6, 3);
		// last graph's width is 20, that's 2x of the display area, so move 6 / 2 -> 3
		// last graph's height is 30, that's 3x of the display area, so move 3 / 3 -> 1
		Assert.assertEquals(new Area(3, 1, 13, 11), this.chart.getDisplayedArea());
	}

	@Test
	public void testConvertToGraphCoordinates() throws Exception {
		this.chart.setDisplayedArea(new Area(60, 60));
		this.chart.lastGraphArea = new Area(10, 20, 30, 50);

		double[] point = this.chart.convertToGraphCoordinates(10, 20);
		Assert.assertEquals(0, point[0], DELTA);
		Assert.assertEquals(60, point[1], DELTA);

		point = this.chart.convertToGraphCoordinates(30, 20);
		Assert.assertEquals(60, point[0], DELTA);
		Assert.assertEquals(60, point[1], DELTA);

		point = this.chart.convertToGraphCoordinates(30, 50);
		Assert.assertEquals(60, point[0], DELTA);
		Assert.assertEquals(0, point[1], DELTA);

		point = this.chart.convertToGraphCoordinates(10, 50);
		Assert.assertEquals(0, point[0], DELTA);
		Assert.assertEquals(0, point[1], DELTA);

		point = this.chart.convertToGraphCoordinates(10, 50);
		Assert.assertEquals(0, point[0], DELTA);
		Assert.assertEquals(0, point[1], DELTA);

		point = this.chart.convertToGraphCoordinates(20, 30);
		Assert.assertEquals(30, point[0], DELTA);
		Assert.assertEquals(40, point[1], DELTA);
	}

	@Test
	public void testConvertToGraphCoordinatesMoved10x20() throws Exception {
		this.chart.setDisplayedArea(new Area(10, 20, 70, 80));
		this.chart.lastGraphArea = new Area(10, 20, 30, 50);

		double[] point = this.chart.convertToGraphCoordinates(10, 20);
		Assert.assertEquals(10, point[0], DELTA);
		Assert.assertEquals(80, point[1], DELTA);

		point = this.chart.convertToGraphCoordinates(30, 20);
		Assert.assertEquals(70, point[0], DELTA);
		Assert.assertEquals(80, point[1], DELTA);

		point = this.chart.convertToGraphCoordinates(30, 50);
		Assert.assertEquals(70, point[0], DELTA);
		Assert.assertEquals(20, point[1], DELTA);

		point = this.chart.convertToGraphCoordinates(10, 50);
		Assert.assertEquals(10, point[0], DELTA);
		Assert.assertEquals(20, point[1], DELTA);

		point = this.chart.convertToGraphCoordinates(10, 50);
		Assert.assertEquals(10, point[0], DELTA);
		Assert.assertEquals(20, point[1], DELTA);

		point = this.chart.convertToGraphCoordinates(20, 30);
		Assert.assertEquals(40, point[0], DELTA);
		Assert.assertEquals(60, point[1], DELTA);
	}

	@Test
	public void testConvertToGraphX() throws Exception {
		this.chart.setDisplayedArea(new Area(60, 60));
		this.chart.lastGraphArea = new Area(10, 20, 30, 50);

		Assert.assertEquals(0, this.chart.convertToGraphX(10), DELTA);
		Assert.assertEquals(30, this.chart.convertToGraphX(20), DELTA);
		Assert.assertEquals(60, this.chart.convertToGraphX(30), DELTA);
	}

	@Test
	public void testConvertToGraphXMoved10x20() throws Exception {
		this.chart.setDisplayedArea(new Area(10, 20, 70, 80));
		this.chart.lastGraphArea = new Area(10, 20, 30, 50);

		Assert.assertEquals(10, this.chart.convertToGraphX(10), DELTA);
		Assert.assertEquals(70, this.chart.convertToGraphX(30), DELTA);
		Assert.assertEquals(70, this.chart.convertToGraphX(30), DELTA);
		Assert.assertEquals(10, this.chart.convertToGraphX(10), DELTA);
		Assert.assertEquals(10, this.chart.convertToGraphX(10), DELTA);
		Assert.assertEquals(40, this.chart.convertToGraphX(20), DELTA);
	}

	@Test
	public void testConvertToGraphY() throws Exception {
		this.chart.setDisplayedArea(new Area(60, 60));
		this.chart.lastGraphArea = new Area(10, 20, 30, 50);

		Assert.assertEquals(60, this.chart.convertToGraphY(20), DELTA);
		Assert.assertEquals(40, this.chart.convertToGraphY(30), DELTA);
		Assert.assertEquals(0, this.chart.convertToGraphY(50), DELTA);
	}

	@Test
	public void testConvertToGraphYMoved10x20() throws Exception {
		this.chart.setDisplayedArea(new Area(10, 20, 70, 80));
		this.chart.lastGraphArea = new Area(10, 20, 30, 50);

		Assert.assertEquals(80, this.chart.convertToGraphY(20), DELTA);
		Assert.assertEquals(60, this.chart.convertToGraphY(30), DELTA);
		Assert.assertEquals(20, this.chart.convertToGraphY(50), DELTA);
	}

	@Test
	public void testZoomDisplayedAreaInByChartCoordinatesCenter() throws Exception {
		this.chart.setDisplayedArea(new Area(10, 20, 70, 80));
		this.chart.lastGraphArea = new Area(10, 20, 30, 50);

		Assert.assertEquals(0.25, this.chart.getZoomFactor(), DELTA);
		this.chart.zoomDisplayedAreaInByChartCoordinates(20, 35);

		// all four sides are zoomed in equally; at 25% zoom and a width / height of 60
		// that's 15 pixels separated equally (div 2)
		final Area result = this.chart.getDisplayedArea();
		Assert.assertEquals(17.5, result.getStartX(), DELTA);
		Assert.assertEquals(27.5, result.getStartY(), DELTA);
		Assert.assertEquals(62.5, result.getEndX(), DELTA);
		Assert.assertEquals(72.5, result.getEndY(), DELTA);
	}

	@Test
	public void testZoomDisplayedAreaOutByChartCoordinatesCenter() throws Exception {
		this.chart.setDisplayedArea(new Area(10, 20, 70, 80));
		this.chart.lastGraphArea = new Area(10, 20, 30, 50);

		Assert.assertEquals(0.25, this.chart.getZoomFactor(), DELTA);
		this.chart.zoomDisplayedAreaOutByChartCoordinates(20, 35);

		// all four sides are zoomed out equally; at 25% zoom and a width / height of 60
		// that's 15 pixels separated equally (div 2)
		final Area result = this.chart.getDisplayedArea();
		Assert.assertEquals(2.5, result.getStartX(), DELTA);
		Assert.assertEquals(12.5, result.getStartY(), DELTA);
		Assert.assertEquals(77.5, result.getEndX(), DELTA);
		Assert.assertEquals(87.5, result.getEndY(), DELTA);
	}

	@Test
	public void testZoomDisplayedAreaInByGraphCoordinatesCenter() throws Exception {
		this.chart.setDisplayedArea(new Area(10, 20, 70, 80));
		this.chart.lastGraphArea = new Area(10, 20, 30, 50);

		Assert.assertEquals(0.25, this.chart.getZoomFactor(), DELTA);
		this.chart.zoomDisplayedAreaInByGraphCoordinates(40, 50);

		// all four sides are zoomed in equally; at 25% zoom and a width / height of 60
		// that's 15 pixels separated equally (div 2)
		final Area result = this.chart.getDisplayedArea();
		Assert.assertEquals(17.5, result.getStartX(), DELTA);
		Assert.assertEquals(27.5, result.getStartY(), DELTA);
		Assert.assertEquals(62.5, result.getEndX(), DELTA);
		Assert.assertEquals(72.5, result.getEndY(), DELTA);
	}

	@Test
	public void testZoomDisplayedAreaOutByGraphCoordinatesCenter() throws Exception {
		this.chart.setDisplayedArea(new Area(10, 20, 70, 80));
		this.chart.lastGraphArea = new Area(10, 20, 30, 50);

		Assert.assertEquals(0.25, this.chart.getZoomFactor(), DELTA);
		this.chart.zoomDisplayedAreaOutByGraphCoordinates(40, 50);

		// all four sides are zoomed out equally; at 25% zoom and a width / height of 60
		// that's 15 pixels separated equally (div 2)
		final Area result = this.chart.getDisplayedArea();
		Assert.assertEquals(2.5, result.getStartX(), DELTA);
		Assert.assertEquals(12.5, result.getStartY(), DELTA);
		Assert.assertEquals(77.5, result.getEndX(), DELTA);
		Assert.assertEquals(87.5, result.getEndY(), DELTA);
	}

	@Test
	public void testZoomDisplayedAreaInByChartCoordinates() throws Exception {
		this.chart.setDisplayedArea(new Area(10, 20, 70, 80));
		this.chart.lastGraphArea = new Area(10, 20, 30, 50);

		Assert.assertEquals(0.25, this.chart.getZoomFactor(), DELTA);
		// that point is 0.25 for x and 0.33 for y
		this.chart.zoomDisplayedAreaInByChartCoordinates(15, 30);

		// the 15 pixels zoom is split according to the zoom point; at 25% zoom and a
		// width / height of 60 that's 15 pixels separated 3.75:11.25 and 5:10
		final Area result = this.chart.getDisplayedArea();
		Assert.assertEquals(13.75, result.getStartX(), DELTA);
		Assert.assertEquals(30, result.getStartY(), DELTA);
		Assert.assertEquals(58.75, result.getEndX(), DELTA);
		Assert.assertEquals(75, result.getEndY(), DELTA);
	}

	@Test
	public void testZoomDisplayedAreaInByGraphCoordinates() throws Exception {
		this.chart.setDisplayedArea(new Area(10, 20, 70, 80));
		this.chart.lastGraphArea = new Area(10, 20, 30, 50);

		Assert.assertEquals(0.25, this.chart.getZoomFactor(), DELTA);
		// that point is 0.25 for x and 0.33 for y
		this.chart.zoomDisplayedAreaInByGraphCoordinates(25, 60);

		// the 15 pixels zoom is split according to the zoom point; at 25% zoom and a
		// width / height of 60 that's 15 pixels separated 3.75:11.25 and 5:10
		final Area result = this.chart.getDisplayedArea();
		Assert.assertEquals(13.75, result.getStartX(), DELTA);
		Assert.assertEquals(30, result.getStartY(), DELTA);
		Assert.assertEquals(58.75, result.getEndX(), DELTA);
		Assert.assertEquals(75, result.getEndY(), DELTA);
	}

	@Test
	public void testZoomDisplayedAreaInByChartCoordinatesWithZoomFactor() throws Exception {
		this.chart.setDisplayedArea(new Area(10, 20, 70, 80));
		this.chart.lastGraphArea = new Area(10, 20, 30, 50);

		this.chart.zoomFactor(0.5);
		Assert.assertEquals(0.5, this.chart.getZoomFactor(), DELTA);
		// that point is 0.25 for x and 0.33 for y
		this.chart.zoomDisplayedAreaInByChartCoordinates(15, 30);

		// the 15 pixels zoom is split according to the zoom point; at 50% zoom and a
		// width / height of 60 that's 30 pixels separated 7.5:22.5 and 10:20
		final Area result = this.chart.getDisplayedArea();
		Assert.assertEquals(17.5, result.getStartX(), DELTA);
		Assert.assertEquals(40, result.getStartY(), DELTA);
		Assert.assertEquals(47.5, result.getEndX(), DELTA);
		Assert.assertEquals(70, result.getEndY(), DELTA);
	}

	@Test
	public void testZoomDisplayedAreaInByGraphCoordinatesWithZoomFactor() throws Exception {
		this.chart.setDisplayedArea(new Area(10, 20, 70, 80));
		this.chart.lastGraphArea = new Area(10, 20, 30, 50);

		this.chart.zoomFactor(0.5);
		Assert.assertEquals(0.5, this.chart.getZoomFactor(), DELTA);
		// that point is 0.25 for x and 0.33 for y
		this.chart.zoomDisplayedAreaInByGraphCoordinates(25, 60);

		// the 15 pixels zoom is split according to the zoom point; at 50% zoom and a
		// width / height of 60 that's 30 pixels separated 7.5:22.5 and 10:20
		final Area result = this.chart.getDisplayedArea();
		Assert.assertEquals(17.5, result.getStartX(), DELTA);
		Assert.assertEquals(40, result.getStartY(), DELTA);
		Assert.assertEquals(47.5, result.getEndX(), DELTA);
		Assert.assertEquals(70, result.getEndY(), DELTA);
	}

	@Test
	public void testResetDisplayedArea() throws Exception {
		this.chart.setDisplayedArea(new Area(1, 2, 3, 4));
		this.chart.resetDisplayedArea();

		Assert.assertEquals(null, this.chart.getDisplayedArea());
	}

	@Test
	public void testConvertToChartX() throws Exception {
		this.chart.setDisplayedArea(new Area(60, 60));
		this.chart.lastGraphArea = new Area(10, 20, 30, 50);

		Assert.assertEquals(10, this.chart.convertToChartX(0), DELTA);
		Assert.assertEquals(20, this.chart.convertToChartX(30), DELTA);
		Assert.assertEquals(30, this.chart.convertToChartX(60), DELTA);
	}

	@Test
	public void testConvertToChartXMoved10x20() throws Exception {
		this.chart.setDisplayedArea(new Area(10, 20, 70, 80));
		this.chart.lastGraphArea = new Area(10, 20, 30, 50);

		Assert.assertEquals(10, this.chart.convertToChartX(10), DELTA);
		Assert.assertEquals(20, this.chart.convertToChartX(40), DELTA);
		Assert.assertEquals(30, this.chart.convertToChartX(70), DELTA);
	}

	@Test
	public void testConvertToChartY() throws Exception {
		this.chart.setDisplayedArea(new Area(60, 60));
		this.chart.lastGraphArea = new Area(10, 20, 30, 50);

		Assert.assertEquals(20, this.chart.convertToChartY(60), DELTA);
		Assert.assertEquals(30, this.chart.convertToChartY(40), DELTA);
		Assert.assertEquals(50, this.chart.convertToChartY(0), DELTA);
	}

	@Test
	public void testConvertToChartYMoved10x20() throws Exception {
		this.chart.setDisplayedArea(new Area(10, 20, 70, 80));
		this.chart.lastGraphArea = new Area(10, 20, 30, 50);

		Assert.assertEquals(20, this.chart.convertToChartY(80), DELTA);
		Assert.assertEquals(30, this.chart.convertToChartY(60), DELTA);
		Assert.assertEquals(50, this.chart.convertToChartY(20), DELTA);
	}

}

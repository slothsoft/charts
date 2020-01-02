package de.slothsoft.charts.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.Font;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;
import de.slothsoft.charts.barchart.BarChart;
import de.slothsoft.charts.linechart.DataPointLine;
import de.slothsoft.charts.linechart.FunctionLine;
import de.slothsoft.charts.linechart.LineChart;
import de.slothsoft.charts.piechart.PieChart;

/**
 * We can't really tests {@link GraphicContext}s without a sophisticated algorithm to
 * verify the resulting images (which is probably better done by a human looking over the
 * images from example-generator), but we can sanity check if calling all methods of the
 * GC will work without an exception.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public abstract class AbstractGraphicContextSanityTest {

	protected static final int WIDTH = 400;
	protected static final int HEIGHT = 300;

	private GraphicContext gc;

	@Before
	public void setUp() {
		this.gc = createGraphicContext();
	}

	protected abstract GraphicContext createGraphicContext();

	@Test
	public void testAllPaintMethods() throws Exception {
		AllMethodsChart.paintAllMethods(this.gc, new Area(WIDTH - 1, HEIGHT - 1));
	}

	@Test
	public void testSetColor() throws Exception {
		this.gc.setColor(0xFF2222BB);

		Assert.assertEquals(0xFF2222BB, this.gc.getColor());
	}

	@Test
	public void testSetFont() throws Exception {
		this.gc.setFont(Font.TITLE);

		Assert.assertEquals(Font.TITLE, this.gc.getFont());
	}

	@Test
	public void testSetFontGettingBigger() throws Exception {
		final Font smallFont = Font.NORMAL;
		final Font bigFont = Font.TITLE;

		Assert.assertTrue("Small font must be smaller than big font!", smallFont.getSize() < bigFont.getSize());

		this.gc.setFont(smallFont);
		final Area smallSize = this.gc.calculateTextSize("O");

		this.gc.setFont(bigFont);
		final Area bigSize = this.gc.calculateTextSize("O");

		Assert.assertTrue(
				"Small font must be smaller than big font: " + smallSize.getEndX() + " < " + bigSize.getEndX(),
				smallSize.getEndX() < bigSize.getEndX());
		Assert.assertTrue(
				"Small font must be smaller than big font: " + smallSize.getEndY() + " < " + bigSize.getEndY(),
				smallSize.getEndY() < bigSize.getEndY());
	}

	@Test
	public void testPaintLineChart() throws Exception {
		final LineChart chart = new LineChart();
		chart.addLine(new DataPointLine(1, 2, 3, 4, 5));
		chart.addLine(new FunctionLine(x -> x * 2 - 1));

		chart.paintOn(this.gc, new PaintInstructions(new Area(100, 100)));
	}

	@Test
	public void testPaintPieChart() throws Exception {
		final PieChart chart = new PieChart();
		chart.addSlices(1, 2, 3, 4, 5);

		chart.paintOn(this.gc, new PaintInstructions(new Area(100, 100)));
	}

	@Test
	public void testPaintBarChart() throws Exception {
		final BarChart chart = new BarChart();
		chart.addBars(1, 2, 3, 4, 5);

		chart.paintOn(this.gc, new PaintInstructions(new Area(100, 100)));
	}
}

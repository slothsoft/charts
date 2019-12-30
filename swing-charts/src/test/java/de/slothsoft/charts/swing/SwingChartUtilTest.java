package de.slothsoft.charts.swing;

import java.awt.image.BufferedImage;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.charts.Chart;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;

public class SwingChartUtilTest {

	@Test
	@SuppressWarnings("static-method")
	public void testCreateImage() throws Exception {
		// we can't test much, only sanity check

		final boolean[] called = {false};
		final Chart chart = new Chart() {
			@Override
			protected void paintGraph(GraphicContext gc, PaintInstructions instructions) {
				called[0] = true;
			}
		};
		final BufferedImage image = SwingChartUtil.createImage(chart, 123, 456);

		Assert.assertTrue(called[0]);
		Assert.assertEquals(123, image.getWidth());
		Assert.assertEquals(456, image.getHeight());
	}
}

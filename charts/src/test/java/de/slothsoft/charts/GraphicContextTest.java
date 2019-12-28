package de.slothsoft.charts;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.charts.internal.LogGraphicContext;

/**
 * We'll test the default methods of the interface here.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class GraphicContextTest {

	private final LogGraphicContext graphicContext = new LogGraphicContext();

	@Test
	public void testScale() throws Exception {
		this.graphicContext.scale(4.2);

		Assert.assertEquals(Arrays.asList("scale(4.2, 4.2)"), this.graphicContext.getLog());
	}

	@Test
	public void testDrawLine() throws Exception {
		this.graphicContext.drawLine(1.2, 3.4, 4.5, 6.7);

		Assert.assertEquals(Arrays.asList("drawPolyline([1.2, 4.5], [3.4, 6.7])"), this.graphicContext.getLog());
	}

	@Test
	public void testFillRectangle() throws Exception {
		this.graphicContext.fillRectangle(new Area(1.2, 3.4, 4.5, 6.8));

		Assert.assertEquals(Arrays.asList("fillRectangle(1.2, 3.4, 3.3, 3.4)"), this.graphicContext.getLog());
	}
}

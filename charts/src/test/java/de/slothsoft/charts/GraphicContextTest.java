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
	public void testFillRectangleArea() throws Exception {
		this.graphicContext.fillRectangle(new Area(1.2, 3.4, 4.5, 6.8));

		Assert.assertEquals(Arrays.asList("fillPolygon([1.2, 4.5, 4.5, 1.2, 1.2], [3.4, 3.4, 6.8, 6.8, 3.4])"),
				this.graphicContext.getLog());
	}

	@Test
	public void testFillRectangle() throws Exception {
		this.graphicContext.fillRectangle(1.2, 3.4, 4.5, 6.8);

		Assert.assertEquals(Arrays.asList("fillPolygon([1.2, 5.7, 5.7, 1.2, 1.2], [3.4, 3.4, 10.2, 10.2, 3.4])"),
				this.graphicContext.getLog());
	}

	@Test
	public void testDrawRectangleArea() throws Exception {
		this.graphicContext.drawRectangle(new Area(1.2, 3.4, 4.5, 6.8));

		Assert.assertEquals(Arrays.asList("drawPolyline([1.2, 4.5, 4.5, 1.2, 1.2], [3.4, 3.4, 6.8, 6.8, 3.4])"),
				this.graphicContext.getLog());
	}

	@Test
	public void testDrawRectangle() throws Exception {
		this.graphicContext.drawRectangle(1.2, 3.4, 4.5, 6.8);

		Assert.assertEquals(Arrays.asList("drawPolyline([1.2, 5.7, 5.7, 1.2, 1.2], [3.4, 3.4, 10.2, 10.2, 3.4])"),
				this.graphicContext.getLog());
	}
}

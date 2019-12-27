package de.slothsoft.charts.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.GraphicContext;

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

	// Note: the same calls are in GraphicsGenerator.AllMethodsChart

	@Test
	public void testAllPaintMethods() throws Exception {
		this.gc.setColor(0xFF2222BB);
		this.gc.fillRectangle(5, 10, 40, 20);

		this.gc.setColor(0xFF2222FF);
		this.gc.fillRectangle(new Area(10, 10, 40, 30));

		this.gc.setColor(0xFF555511);
		this.gc.clip(new Area(60, 10, 80, 41));
		this.gc.drawPolyline(new double[]{70, 90, 50, 70}, new double[]{10, 40, 40, 10});
		this.gc.clip(null);

		this.gc.setColor(0xFF115511);
		this.gc.drawLine(60, 50, 80, 50);
	}

	@Test
	public void testSetColor() throws Exception {
		this.gc.setColor(0xFF2222BB);

		Assert.assertEquals(0xFF2222BB, this.gc.getColor());
	}
}

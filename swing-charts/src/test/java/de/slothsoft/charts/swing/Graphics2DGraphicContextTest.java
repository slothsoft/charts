package de.slothsoft.charts.swing;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Graphics2DGraphicContextTest {

	private Graphics2D graphics2D;
	private Graphics2DGraphicContext graphicContext;

	@Before
	public void setUp() {
		final BufferedImage image = new BufferedImage(400, 300, BufferedImage.TYPE_INT_ARGB);
		this.graphics2D = image.createGraphics();
		this.graphicContext = new Graphics2DGraphicContext(this.graphics2D);
	}

	@After
	public void tearDown() {
		this.graphics2D.dispose();
	}

	@Test
	public void testSetDelegate() throws Exception {
		final BufferedImage otherImage = new BufferedImage(400, 300, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D otherGraphics2D = otherImage.createGraphics();
		try {
			this.graphicContext.setDelegate(otherGraphics2D);
			Assert.assertSame(otherGraphics2D, this.graphicContext.getDelegate());
		} finally {
			otherGraphics2D.dispose();
		}

		this.graphicContext.delegate(this.graphics2D);
		Assert.assertSame(this.graphics2D, this.graphicContext.getDelegate());
	}
}

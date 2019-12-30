package de.slothsoft.charts.swing;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.junit.After;

import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.test.AbstractGraphicContextSanityTest;

public class Graphics2DGraphicContextSanityTest extends AbstractGraphicContextSanityTest {

	private Graphics2D graphics2D;

	@Override
	protected GraphicContext createGraphicContext() {
		final BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		this.graphics2D = image.createGraphics();
		return new Graphics2DGraphicContext(this.graphics2D);
	}

	@After
	public void tearDown() {
		this.graphics2D.dispose();
	}

}

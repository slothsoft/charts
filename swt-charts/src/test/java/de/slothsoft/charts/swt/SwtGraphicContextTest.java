package de.slothsoft.charts.swt;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Display;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SwtGraphicContextTest {

	@Rule
	public SwtRule swtRule = new SwtRule();

	private Image image;
	private GC gc;
	private SwtGraphicContext graphicContext;

	@Before
	public void setUp() {
		this.image = new Image(Display.getDefault(), 400, 300);
		this.gc = new GC(this.image);
		this.graphicContext = new SwtGraphicContext(this.gc);
	}

	@After
	public void tearDown() {
		this.gc.dispose();
		this.image.dispose();
	}

	@Test
	public void testSetDelegate() throws Exception {
		final Image otherImage = new Image(Display.getDefault(), 400, 300);
		final GC otherGc = new GC(otherImage);
		try {
			this.graphicContext.setDelegate(otherGc);
			Assert.assertSame(otherGc, this.graphicContext.getDelegate());
		} finally {
			otherGc.dispose();
			otherImage.dispose();
		}

		this.graphicContext.delegate(this.gc);
		Assert.assertSame(this.gc, this.graphicContext.getDelegate());
	}

	@Test
	public void testDisposeTwice() throws Exception {
		this.graphicContext.dispose();
		this.graphicContext.dispose();
		Assert.assertTrue("Everything ok.", true);
	}

	@Test
	public void testDisposeTransform() throws Exception {
		Assert.assertNull(this.graphicContext.transform);
		this.graphicContext.translate(5, 7);
		Assert.assertNotNull(this.graphicContext.transform);

		final Transform transform = this.graphicContext.transform;
		this.graphicContext.dispose();

		Assert.assertTrue(transform.isDisposed());
	}

	@Test
	public void testDisposeFont() throws Exception {
		Assert.assertNull(this.graphicContext.font);
		this.graphicContext.setFont(de.slothsoft.charts.Font.TITLE);
		Assert.assertNotNull(this.graphicContext.font);
		Assert.assertSame(this.graphicContext.font, this.gc.getFont());

		final Font font = this.graphicContext.font;
		this.graphicContext.dispose();

		Assert.assertTrue(font.isDisposed());
		Assert.assertNotSame(this.graphicContext.font, this.gc.getFont());
	}

	@Test
	public void testDisposeColor() throws Exception {
		Assert.assertNull(this.graphicContext.color);
		this.graphicContext.setColor(0xFF123456);
		Assert.assertNotNull(this.graphicContext.color);
		Assert.assertEquals(this.graphicContext.color, this.gc.getBackground());
		Assert.assertEquals(this.graphicContext.color, this.gc.getForeground());

		final Color color = this.graphicContext.color;
		this.graphicContext.dispose();

		Assert.assertTrue(color.isDisposed());
		Assert.assertNotEquals(this.graphicContext.color, this.gc.getBackground());
		Assert.assertNotEquals(this.graphicContext.color, this.gc.getForeground());
	}
}

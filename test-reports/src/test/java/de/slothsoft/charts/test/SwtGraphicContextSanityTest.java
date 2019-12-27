package de.slothsoft.charts.test;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.junit.After;
import org.junit.Rule;

import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.swt.SwtGraphicContext;

public class SwtGraphicContextSanityTest extends AbstractGraphicContextSanityTest {

	@Rule
	public SwtRule swtRule = new SwtRule();

	private Image image;
	private GC gc;

	@Override
	protected GraphicContext createGraphicContext() {
		this.image = new Image(Display.getDefault(), WIDTH, HEIGHT);
		this.gc = new GC(this.image);
		return new SwtGraphicContext(this.gc);
	}

	@After
	public void tearDown() {
		this.gc.dispose();
		this.image.dispose();
	}

}

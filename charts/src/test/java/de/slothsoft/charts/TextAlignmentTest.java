package de.slothsoft.charts;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.slothsoft.charts.internal.LogGraphicContext;

public class TextAlignmentTest {

	private static final String TEXT = "HEY";
	private static final String CALCULATE_TEXT_SIZE = "calculateTextSize(HEY)";

	private final LogGraphicContext logGc = new LogGraphicContext();

	@Before
	public void setUp() {
		Assert.assertEquals(new Area(30, 10), this.logGc.calculateTextSize(TEXT));
		this.logGc.getLog().clear();
	}

	@Test
	public void testDrawTextLeft() throws Exception {
		TextAlignment.LEFT.drawText(this.logGc, new Area(10, 20, 50, 40), TEXT);

		Assert.assertEquals(Arrays.asList("drawText(10.0, 20.0, HEY)"), this.logGc.getLog());
	}

	@Test
	public void testDrawTextCenter() throws Exception {
		TextAlignment.CENTER.drawText(this.logGc, new Area(10, 20, 50, 40), TEXT);

		Assert.assertEquals(Arrays.asList(CALCULATE_TEXT_SIZE, "drawText(15.0, 20.0, HEY)"), this.logGc.getLog());
	}

	@Test
	public void testDrawTextRight() throws Exception {
		TextAlignment.RIGHT.drawText(this.logGc, new Area(10, 20, 50, 40), TEXT);

		Assert.assertEquals(Arrays.asList(CALCULATE_TEXT_SIZE, "drawText(20.0, 20.0, HEY)"), this.logGc.getLog());
	}
}

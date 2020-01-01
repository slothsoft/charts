package de.slothsoft.charts.barchart;

import org.junit.Assert;
import org.junit.Test;

public class BarTest {

	private static final double DELTA = 0.001;

	private final Bar bar = new Bar(1);

	@Test
	public void testSetColor() throws Exception {
		this.bar.color(0xFFABCDEF);
		Assert.assertEquals(0xFFABCDEF, this.bar.getColor());

		this.bar.setColor(0xFFFEDCBA);
		Assert.assertEquals(0xFFFEDCBA, this.bar.getColor());
	}

	@Test
	public void testSetValue() throws Exception {
		this.bar.value(1);
		Assert.assertEquals(1, this.bar.getValue(), DELTA);

		this.bar.setValue(2);
		Assert.assertEquals(2, this.bar.getValue(), DELTA);
	}
}

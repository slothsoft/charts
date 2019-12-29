package de.slothsoft.charts.piechart;

import org.junit.Assert;
import org.junit.Test;

public class PieSliceTest {

	private static final double DELTA = 0.001;

	private final PieSlice slice = new PieSlice(1);

	@Test
	public void testSetColor() throws Exception {
		this.slice.color(0xFFABCDEF);
		Assert.assertEquals(0xFFABCDEF, this.slice.getColor());

		this.slice.setColor(0xFFFEDCBA);
		Assert.assertEquals(0xFFFEDCBA, this.slice.getColor());
	}

	@Test
	public void testSetValue() throws Exception {
		this.slice.value(1);
		Assert.assertEquals(1, this.slice.getValue(), DELTA);

		this.slice.setValue(2);
		Assert.assertEquals(2, this.slice.getValue(), DELTA);
	}
}

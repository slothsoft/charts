package de.slothsoft.charts.linechart;

import org.junit.Assert;
import org.junit.Test;

public class XAxisTest {

	private final XAxis axis = new XAxis(new LineChart());

	@Test
	public void testSetPosition() throws Exception {
		this.axis.setPosition(XAxis.Position.BOTTOM);
		Assert.assertEquals(XAxis.Position.BOTTOM, this.axis.getPosition());

		this.axis.position(XAxis.Position.TOP);
		Assert.assertEquals(XAxis.Position.TOP, this.axis.getPosition());
	}
}

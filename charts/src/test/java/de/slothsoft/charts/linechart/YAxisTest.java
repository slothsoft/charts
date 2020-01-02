package de.slothsoft.charts.linechart;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.charts.linechart.LineChart;
import de.slothsoft.charts.linechart.YAxis;

public class YAxisTest {

	private final YAxis axis = new YAxis(new LineChart());

	@Test
	public void testSetPosition() throws Exception {
		this.axis.setPosition(YAxis.Position.LEFT_AND_RIGHT);
		Assert.assertEquals(YAxis.Position.LEFT_AND_RIGHT, this.axis.getPosition());

		this.axis.position(YAxis.Position.LEFT);
		Assert.assertEquals(YAxis.Position.LEFT, this.axis.getPosition());
	}
}

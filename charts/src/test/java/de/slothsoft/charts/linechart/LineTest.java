package de.slothsoft.charts.linechart;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.charts.Area;

public class LineTest {

	@Test
	public void testCreateDefaultArea() throws Exception {
		Assert.assertEquals(
				new Area(Line.DEFAULT_START_X, Line.DEFAULT_START_Y, Line.DEFAULT_END_X, Line.DEFAULT_END_Y),
				Line.createDefaultArea());
	}
}

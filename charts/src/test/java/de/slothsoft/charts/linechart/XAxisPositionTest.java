package de.slothsoft.charts.linechart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.DoubleConsumer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.slothsoft.charts.linechart.XAxis;
import de.slothsoft.charts.linechart.XAxis.Position;

@RunWith(Parameterized.class)
public class XAxisPositionTest {

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		final List<Object[]> result = new ArrayList<>();
		for (final XAxis.Position position : XAxis.Position.values()) {
			if (position != Position.NOWHERE) {
				result.add(new Object[]{position});
			}
		}
		return result;
	}

	private final XAxis.Position position;

	public XAxisPositionTest(Position position) {
		this.position = position;
	}

	@Test
	public void testPaintAxisPaintsAtLeastOneAxis() throws Exception {
		final boolean[] called = {false};
		final DoubleConsumer axisPainter = value -> called[0] = true;
		this.position.paintAxis(axisPainter, 1, 2, 3);

		Assert.assertTrue(called[0]);
	}
}

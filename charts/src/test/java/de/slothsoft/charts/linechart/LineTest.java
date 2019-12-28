package de.slothsoft.charts.linechart;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;
import de.slothsoft.charts.RefreshListener;

public class LineTest {

	private final Line line = new Line() {

		@Override
		public void paintOn(GraphicContext gc, PaintInstructions instructions) {
			// nothing to do in test
		}

		@Override
		protected Area calculatePreferredArea() {
			return null;
		}
	};

	@Test
	@SuppressWarnings("static-method")
	public void testCreateDefaultArea() throws Exception {
		Assert.assertEquals(
				new Area(Line.DEFAULT_START_X, Line.DEFAULT_START_Y, Line.DEFAULT_END_X, Line.DEFAULT_END_Y),
				Line.createDefaultArea());
	}

	@Test
	public void testColor() throws Exception {
		this.line.color(0xFFABCDEF);
		Assert.assertEquals(0xFFABCDEF, this.line.getColor());

		this.line.setColor(0xFFFEDCBA);
		Assert.assertEquals(0xFFFEDCBA, this.line.getColor());
	}

	@Test
	public void testAddRefreshListener() throws Exception {
		final RefreshListener.Event[] called = {null};
		this.line.addRefreshListener(e -> called[0] = e);
		this.line.setColor(0xFFEDCBA0);

		Assert.assertNotNull(called[0]);
		Assert.assertNotNull(called[0].getSource());
	}

	@Test
	public void testRemoveRefreshListener() throws Exception {
		final RefreshListener.Event[] called = {null};
		final RefreshListener listener = e -> called[0] = e;
		this.line.addRefreshListener(listener);
		this.line.removeRefreshListener(listener);
		this.line.setColor(0xFFEDCBA0);

		Assert.assertNull(called[0]);
	}
}

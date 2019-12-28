package de.slothsoft.charts.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.Font;
import de.slothsoft.charts.PaintInstructions;
import de.slothsoft.charts.TextAlignment;
import de.slothsoft.charts.common.Title.Position;
import de.slothsoft.charts.internal.LogGraphicContext;

@RunWith(Parameterized.class)
public class TitlePositionTest {

	private static final String TEXT = "LOL";

	@Parameters(name = "{0} ({1})")
	public static Collection<Object[]> data() {
		final List<Object[]> result = new ArrayList<>();
		final Area area = new Area(10, 20, 50, 40);
		// default: position=TOP, alignment=CENTER, size=25, font=NORMAL
		result.add(data(createTitle(), area, 15, 20));

		// first try all positions
		result.add(data(createTitle().position(Position.TOP), area, 15, 20));
		result.add(data(createTitle().position(Position.BOTTOM), area, 15, 15));

		// now all text alignments
		result.add(data(createTitle().alignment(TextAlignment.CENTER), area, 15, 20));
		result.add(data(createTitle().alignment(TextAlignment.LEFT), area, 10, 20));
		result.add(data(createTitle().alignment(TextAlignment.RIGHT), area, 20, 20));

		// then some sizes
		result.add(data(createTitle().size(30), area, 15, 20));

		// another font (NORMAL=10, TITLE=14)
		result.add(data(createTitle().font(Font.TITLE), area, 9, 20));

		// other text
		result.add(data(createTitle().text(null), area, Double.NaN, Double.NaN));

		// other area
		result.add(data(createTitle(), new Area(100, 200, 500, 400), 285, 200));
		result.add(data(createTitle(), new Area(1, 2, 5, 4), -12, 2));

		// and finally everything at once
		result.add(data(createTitle().position(Position.BOTTOM).alignment(TextAlignment.RIGHT), area, 20, 15));
		result.add(data(createTitle().size(30).position(Position.BOTTOM), area, 15, 10));

		return result;
	}

	private static Title createTitle() {
		return new Title().text(TEXT).font(Font.NORMAL);
	}

	private static Object[] data(Title title, Area area, double expectedX, double expectedY) {
		return new Object[]{title, area, Double.valueOf(expectedX), Double.valueOf(expectedY)};
	}

	private final Title title;
	private final Area area;
	private final double expectedX;
	private final double expectedY;

	private LogGraphicContext graphicContext;

	public TitlePositionTest(Title title, Area area, double expectedX, double expectedY) {
		this.title = title;
		this.area = area;
		this.expectedX = expectedX;
		this.expectedY = expectedY;
	}

	@Before
	public void setUp() {
		this.graphicContext = new LogGraphicContext();
		Assert.assertEquals(new Area(30, 10), this.graphicContext.calculateTextSize(TEXT));
	}

	@Test
	public void test() throws Exception {
		this.title.paintOn(this.graphicContext, new PaintInstructions(this.area));

		if (Double.isNaN(this.expectedX)) {
			for (final String log : this.graphicContext.getLog()) {
				Assert.assertFalse("Text shouldn't have been drawn!", log.startsWith("drawText"));
			}
		} else {
			final String log = getLastLog();

			Assert.assertEquals("drawText(" + this.expectedX + ", " + this.expectedY + ", " + TEXT + ")", log);
		}
	}

	private String getLastLog() {
		final List<String> log = this.graphicContext.getLog();
		Assert.assertFalse("Log has no entries!", log.isEmpty());
		return log.get(log.size() - 1);
	}

}

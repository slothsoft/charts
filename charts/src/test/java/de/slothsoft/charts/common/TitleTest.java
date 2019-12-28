package de.slothsoft.charts.common;

import org.junit.Assert;
import org.junit.Test;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.Font;
import de.slothsoft.charts.RefreshListener;

public class TitleTest {

	private static final double DELTA = 0.001;

	private final Title title = new Title();

	@Test
	public void testSnipNecessarySpace() throws Exception {
		this.title.size(7).text("❤");
		final Area result = this.title.snipNecessarySpace(new Area(10, 20, 30, 40));

		Assert.assertEquals(10, result.getStartX(), DELTA);
		Assert.assertEquals(27, result.getStartY(), DELTA);
		Assert.assertEquals(30, result.getEndX(), DELTA);
		Assert.assertEquals(40, result.getEndY(), DELTA);
	}

	@Test
	public void testSnipNecessarySpaceNoText() throws Exception {
		this.title.size(7);
		final Area result = this.title.snipNecessarySpace(new Area(10, 20, 30, 40));

		Assert.assertEquals(10, result.getStartX(), DELTA);
		Assert.assertEquals(20, result.getStartY(), DELTA);
		Assert.assertEquals(30, result.getEndX(), DELTA);
		Assert.assertEquals(40, result.getEndY(), DELTA);
	}

	@Test
	public void testColor() throws Exception {
		this.title.color(0xFFABCDEF);
		Assert.assertEquals(0xFFABCDEF, this.title.getColor());

		this.title.setColor(0xFFFEDCBA);
		Assert.assertEquals(0xFFFEDCBA, this.title.getColor());
	}

	@Test
	public void testFont() throws Exception {
		this.title.font(Font.NORMAL);
		Assert.assertEquals(Font.NORMAL, this.title.getFont());

		this.title.setFont(Font.TITLE);
		Assert.assertEquals(Font.TITLE, this.title.getFont());
	}

	@Test
	public void testText() throws Exception {
		this.title.text("Hello");
		Assert.assertEquals("Hello", this.title.getText());

		this.title.setText("World");
		Assert.assertEquals("World", this.title.getText());
	}

	@Test
	public void testSize() throws Exception {
		this.title.size(1);
		Assert.assertEquals(1, this.title.getSize());

		this.title.setSize(2);
		Assert.assertEquals(2, this.title.getSize());
	}

	@Test
	public void testAddRefreshListener() throws Exception {
		final RefreshListener.Event[] called = {null};
		this.title.addRefreshListener(e -> called[0] = e);
		this.title.setSize(10);

		Assert.assertNotNull(called[0]);
		Assert.assertNotNull(called[0].getSource());
	}

	@Test
	public void testRemoveRefreshListener() throws Exception {
		final RefreshListener.Event[] called = {null};
		final RefreshListener listener = e -> called[0] = e;
		this.title.addRefreshListener(listener);
		this.title.removeRefreshListener(listener);
		this.title.setSize(10);

		Assert.assertNull(called[0]);
	}
}

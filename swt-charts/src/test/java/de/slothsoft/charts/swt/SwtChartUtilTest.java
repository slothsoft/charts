package de.slothsoft.charts.swt;

import org.eclipse.swt.graphics.Image;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import de.slothsoft.charts.Chart;

public class SwtChartUtilTest {

	@Rule
	public SwtRule swtRule = new SwtRule();

	@Test
	@SuppressWarnings("static-method")
	public void testCreateImage() throws Exception {
		// we can't test much, only sanity check

		final boolean[] called = {false};
		final Chart chart = new TestChart().paintFunction((gc, i) -> called[0] = true);
		final Image image = SwtChartUtil.createImage(chart, 123, 456);

		Assert.assertTrue(called[0]);
		Assert.assertEquals(123, image.getBounds().width);
		Assert.assertEquals(456, image.getBounds().height);
	}
}

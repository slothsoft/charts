package de.slothsoft.charts.swing;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.Chart;
import de.slothsoft.charts.PaintInstructions;

/**
 * Some util classes to generate stuff surrounding the {@link Chart} in Swing.
 *
 * @author Stef Schulz
 * @since 0.2.0
 */

public final class SwingChartUtil {

	/**
	 * Creates an {@link BufferedImage} from a chart.
	 *
	 * @param chart the chart to create an image for
	 * @param imageWidth the image's width
	 * @param imageHeight the image's height
	 * @return an image
	 */

	public static BufferedImage createImage(Chart chart, int imageWidth, int imageHeight) {
		final BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D graphics2D = image.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		try {
			chart.paintOn(new Graphics2DGraphicContext(graphics2D),
					new PaintInstructions(new Area(imageWidth, imageHeight)));
			return image;
		} finally {
			graphics2D.dispose();
		}
	}

	private SwingChartUtil() {
		// hide util class
	}
}

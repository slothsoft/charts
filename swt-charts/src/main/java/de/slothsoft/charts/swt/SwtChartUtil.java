package de.slothsoft.charts.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.Chart;
import de.slothsoft.charts.PaintInstructions;

/**
 * Some util classes to generate stuff surrounding the {@link Chart} in SWT.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public final class SwtChartUtil {

	/**
	 * Creates an {@link Image} from a chart.
	 *
	 * @param chart the chart to create an image for
	 * @param imageWidth the image's width
	 * @param imageHeight the image's height
	 * @return an image
	 */

	public static Image createImage(Chart chart, int imageWidth, int imageHeight) {
		final Image image = new Image(Display.getDefault(), imageWidth, imageHeight);
		final GC gc = new GC(image);
		gc.setAntialias(SWT.ON);
		try {
			chart.paintOn(new SwtGraphicContext(gc), new PaintInstructions(new Area(imageWidth, imageHeight)));
			return image;
		} finally {
			gc.dispose();
		}
	}

	private SwtChartUtil() {
		// hide util class
	}
}

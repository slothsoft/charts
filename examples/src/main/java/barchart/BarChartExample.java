package barchart;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import de.slothsoft.charts.Chart;
import de.slothsoft.charts.barchart.BarChart;
import de.slothsoft.charts.swing.SwingChartUtil;

/**
 * This example tries to outline the features of the {@link BarChart}.
 *
 * @author Stef Schulz
 * @since 0.2.0
 */

public class BarChartExample {

	public static void main(String... args) throws IOException {
		final Chart chart = createChart();

		// create and open the chart image

		final Path imageFile = Files.createTempFile(null, ".png");
		final BufferedImage image = SwingChartUtil.createImage(chart, 400, 300);
		ImageIO.write(image, "PNG", imageFile.toFile());
		Desktop.getDesktop().open(imageFile.toFile());
	}

	public static Chart createChart() {
		final BarChart chart = new BarChart();
		chart.setBackgroundColor(0xFFFFFFFF);

		chart.getBorder().setSpaceOnBottom(7);
		chart.getTitle().setText("Bar Chart");

		chart.addBar(6);
		chart.addBars(3, 4);

		return chart;
	}
}

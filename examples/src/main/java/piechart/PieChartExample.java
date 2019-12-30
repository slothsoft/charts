package piechart;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import de.slothsoft.charts.Chart;
import de.slothsoft.charts.piechart.PieChart;
import de.slothsoft.charts.swing.SwingChartUtil;

/**
 * This example tries to outline the features of the {@link PieChart}.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class PieChartExample {

	public static void main(String... args) throws IOException {
		final Chart chart = createChart();

		// create and open the chart image

		final Path imageFile = Files.createTempFile(null, ".png");
		final BufferedImage image = SwingChartUtil.createImage(chart, 400, 300);
		ImageIO.write(image, "PNG", imageFile.toFile());
		Desktop.getDesktop().open(imageFile.toFile());
	}

	public static Chart createChart() {
		final PieChart chart = new PieChart();
		chart.setBackgroundColor(0xFFFFFFFF);

		chart.getBorder().setSpaceOnBottom(7);
		chart.getTitle().setText("Pie Chart");

		chart.addSlice(3);
		chart.addSlices(4, 5);

		return chart;
	}
}

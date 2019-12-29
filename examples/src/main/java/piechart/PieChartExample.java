package piechart;

import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

import de.slothsoft.charts.Chart;
import de.slothsoft.charts.piechart.PieChart;
import de.slothsoft.charts.swt.SwtChartUtil;

/**
 * This example tries to outline the features of the {@link PieChart}.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class PieChartExample {

	public static void main(String... args) throws IOException {
		final Chart chart = createChart();

		final Path imageFile = Files.createTempFile(null, ".png");
		final Image image = SwtChartUtil.createImage(chart, 600, 400);

		final ImageLoader saver = new ImageLoader();
		saver.data = new ImageData[]{image.getImageData()};
		saver.save(imageFile.toString(), SWT.IMAGE_PNG);
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

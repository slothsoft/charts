package swing;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import de.slothsoft.charts.Chart;
import de.slothsoft.charts.linechart.DataPointLine;
import de.slothsoft.charts.linechart.FunctionLine;
import de.slothsoft.charts.linechart.LineChart;
import de.slothsoft.charts.swing.SwingChartUtil;

/**
 * This example displays how to create an SWT {@link BufferedImage} from a {@link Chart}.
 *
 * @author Stef Schulz
 * @since 0.2.0
 */

public class SwingImageExample {

	public static void main(String[] args) throws IOException {

		final LineChart chart = new LineChart();
		chart.addLine(new DataPointLine(-3, -2, -1, 0, 1, 2, 2.5, 2.75, 2.82, 2.91, 2.95));
		chart.addLine(new FunctionLine(x -> Math.cos(x)).color(0xFF00FFFF));

		// store chart on disk and open it

		final Path imageFile = Files.createTempFile(null, ".png");
		writeChartToFile(imageFile, chart);
		Desktop.getDesktop().open(imageFile.toFile());
	}

	public static final void writeChartToFile(Path imageFile, Chart chart) {
		final BufferedImage image = SwingChartUtil.createImage(chart, 400, 300);

		try {
			ImageIO.write(image, "PNG", imageFile.toFile());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}

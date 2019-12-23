package swt;
import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

import de.slothsoft.charts.Chart;
import de.slothsoft.charts.linechart.DataPointLine;
import de.slothsoft.charts.linechart.FunctionLine;
import de.slothsoft.charts.linechart.LineChart;
import de.slothsoft.charts.swt.SwtChartUtil;

/**
 * This example displays how to create an SWT {@link Image} from a {@link Chart}.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class SwtImageExample {

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
		final Image image = SwtChartUtil.createImage(chart, 400, 300);

		final ImageLoader saver = new ImageLoader();
		saver.data = new ImageData[]{image.getImageData()};
		saver.save(imageFile.toString(), SWT.IMAGE_PNG);
	}
}

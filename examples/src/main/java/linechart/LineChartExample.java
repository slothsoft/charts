package linechart;

import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.Chart;
import de.slothsoft.charts.linechart.DataPointLine;
import de.slothsoft.charts.linechart.FunctionLine;
import de.slothsoft.charts.linechart.LineChart;
import de.slothsoft.charts.linechart.XAxis;
import de.slothsoft.charts.linechart.YAxis;
import de.slothsoft.charts.swt.SwtChartUtil;

/**
 * This example tries to outline the features of the {@link LineChart}.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class LineChartExample {

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
		final LineChart chart = new LineChart();
		chart.setBackgroundColor(0xFFFFFFFF);
		chart.setDisplayedArea(new Area(-1, -4, 10, 4));

		chart.getBorder().setSpaceOnBottom(7);
		chart.getXAxis().setPosition(XAxis.Position.Y0);
		chart.getYAxis().setPosition(YAxis.Position.X0);
		chart.getTitle().setText("Line Chart");

		chart.addLine(new DataPointLine(-9, -4, -2, 0, 1, 2, 2.5, 3, 3.3, 3.6, 4).color(0xFFFF00FF));
		chart.addLine(new FunctionLine(x -> Math.sin(x)).color(0xFF00FF00));

		return chart;
	}
}

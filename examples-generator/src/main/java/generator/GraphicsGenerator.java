package generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.Chart;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;
import javafx.scene.canvas.GraphicsContext;

/**
 * This class generates example files for implementations of {@link GraphicsContext}.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class GraphicsGenerator {

	private static final Path TARGET_FOLDER = Paths.get("target/");

	public static void main(String... args) throws IOException {
		Path targetFolder = TARGET_FOLDER;
		if (args.length > 0) {
			targetFolder = Paths.get(args[0]);
		}
		Files.createDirectories(targetFolder);
		System.out.println("GraphicsGenerator: " + targetFolder.toAbsolutePath());
		final StringBuilder overviewPage = new StringBuilder();

		// generate images and fill the table

		for (final ChartWriter writer : ChartWriter.ALL) {
			overviewPage.append("# ").append(writer.displayName).append("\n\n");
			final Chart chart = new AllMethodsChart();
			final Path chartPath = targetFolder.resolve(writer.displayName.toLowerCase() + ".png");
			writer.chartWriter.accept(chartPath, chart);
			System.out.println("Generated file: " + chartPath);
			overviewPage.append("![").append(writer.displayName).append("](gc-examples/")
					.append(chartPath.getFileName()).append(") ");
			overviewPage.append("\n\n");
		}
		Files.write(targetFolder.resolve("GUI Examples.md"), overviewPage.toString().getBytes());
	}

	static class AllMethodsChart extends Chart {

		@Override
		protected void paintGraph(GraphicContext gc, PaintInstructions instructions) {
			gc.setColor(0xFF2222BB);
			gc.fillRectangle(5, 10, 40, 20);

			gc.setColor(0xFF2222FF);
			gc.fillRectangle(new Area(10, 10, 40, 30));

			gc.setColor(0xFF555511);
			gc.clip(new Area(60, 10, 80, 41));
			gc.drawPolyline(new double[]{70, 90, 50, 70}, new double[]{10, 40, 40, 10});
			gc.clip(null);

			gc.setColor(0xFF115511);
			gc.drawLine(60, 50, 80, 50);
		}
	}
}

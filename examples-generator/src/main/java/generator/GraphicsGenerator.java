package generator;

import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import de.slothsoft.charts.Chart;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.test.AllMethodsChart;
import generator.internal.ChartWriter;

/**
 * This class generates example files for implementations of {@link GraphicContext}.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class GraphicsGenerator {

	private static final Path TARGET_FOLDER = Paths.get("target/");
	private static final boolean OPEN_FILES = false;

	public static void main(String... args) throws IOException {
		Path targetFolder = TARGET_FOLDER;
		if (args.length > 0) {
			targetFolder = Paths.get(args[0]);
		}
		Files.createDirectories(targetFolder);
		System.out.println("GraphicsGenerator: " + targetFolder.toAbsolutePath());
		final StringBuilder overviewPage = new StringBuilder();
		overviewPage.append(
				"These images are generated using [these methods](https://github.com/slothsoft/charts/blob/master/gui-tests/src/main/java/de/slothsoft/charts/test/AllMethodsChart.java).\n\n");

		// generate images and fill the table

		for (final ChartWriter writer : ChartWriter.ALL) {
			overviewPage.append("# ").append(writer.displayName).append("\n\n");
			final Chart chart = new AllMethodsChart();
			final Path chartPath = targetFolder.resolve(writer.displayName.toLowerCase() + ".png");
			writer.chartWriter.accept(chartPath, chart);
			System.out.println("Generated file: " + chartPath);
			overviewPage.append(writer.createImageMarkdown(chartPath)).append(" ");
			overviewPage.append("\n\n");

			if (OPEN_FILES) {
				Desktop.getDesktop().open(chartPath.toFile());
			}
		}
		Files.write(targetFolder.resolve("GUI Examples.md"), overviewPage.toString().getBytes());
	}
}

package generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import de.slothsoft.charts.Chart;
import generator.internal.ChartGenerator;
import generator.internal.ChartWriter;

/**
 * This class generates example files for all types of charts AND all types of GUI
 * implementation. This class isn't an example for anything. Don't look at it.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class ChartsGenerator {

	private static final Path TARGET_FOLDER = Paths.get("target/");

	public static void main(String... args) throws IOException {
		Path targetFolder = TARGET_FOLDER;
		if (args.length > 0) {
			targetFolder = Paths.get(args[0]);
		}
		Files.createDirectories(targetFolder);
		System.out.println("ChartsGenerator: " + targetFolder.toAbsolutePath());
		final StringBuilder overviewPage = new StringBuilder();

		// generate the table header of the wiki page

		overviewPage.append("| ! ");
		for (final ChartWriter writer : ChartWriter.ALL) {
			overviewPage.append("| ").append(writer.displayName).append(' ');
		}
		overviewPage.append("|\n");
		overviewPage.append(overviewPage.toString().replaceAll("[a-zA-Z!]", "-"));
		overviewPage.setCharAt(2, ' ');

		// generate images and fill the table

		for (final ChartGenerator generator : ChartGenerator.ALL) {
			overviewPage.append("| ").append("[").append(generator.chartClass.getSimpleName())
					.append("](https://slothsoft.github.io/charts/")
					.append(generator.chartClass.getCanonicalName().replace('.', '/')).append(") ");
			for (final ChartWriter writer : ChartWriter.ALL) {
				final Chart chart = generator.chartGenerator.get();
				final Path chartPath = targetFolder
						.resolve(generator.displayName.toLowerCase() + "-" + writer.displayName.toLowerCase() + ".png");
				writer.chartWriter.accept(chartPath, chart);
				System.out.println("Generated file: " + chartPath);
				overviewPage.append("| ").append(writer.createImageMarkdown(chartPath)).append(" ");
			}
			overviewPage.append("|\n");
		}
		Files.write(targetFolder.resolve("Chart Examples.md"), overviewPage.toString().getBytes());
	}
}

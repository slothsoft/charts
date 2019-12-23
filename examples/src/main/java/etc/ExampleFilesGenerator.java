package etc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import de.slothsoft.charts.Chart;
import de.slothsoft.charts.linechart.LineChart;
import linechart.LineChartExample;
import swt.SwtImageExample;

/**
 * This class generates example files for all types of charts AND all types of GUI
 * implementation. This class isn't an example for anything. Don't look at it.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class ExampleFilesGenerator {

	private static final ChartGenerator[] CHART_GENERATORS = {

			new ChartGenerator("LineChart", LineChart.class, LineChartExample::createChart),

	};

	private static class ChartGenerator {

		String displayName;
		Class<?> chartClass;
		Supplier<Chart> chartGenerator;

		public ChartGenerator(String displayName, Class<?> chartClass, Supplier<Chart> chartGenerator) {
			this.displayName = displayName;
			this.chartClass = chartClass;
			this.chartGenerator = chartGenerator;
		}
	}

	private static final ChartWriter[] CHART_WRITERS = {

			new ChartWriter("SWT", SwtImageExample::writeChartToFile),

	};

	private static class ChartWriter {

		String displayName;
		BiConsumer<Path, Chart> chartWriter;

		public ChartWriter(String displayName, BiConsumer<Path, Chart> chartWriter) {
			this.displayName = displayName;
			this.chartWriter = chartWriter;
		}
	}

	private static final Path TARGET_FOLDER = Paths.get("target/");

	public static void main(String[] args) throws IOException {
		Path targetFolder = TARGET_FOLDER;
		if (args.length > 0) {
			targetFolder = Paths.get(args[0]);
		}
		Files.createDirectories(targetFolder);
		System.out.println("ExampleFilesGenerator: " + targetFolder.toAbsolutePath());
		final StringBuilder overviewPage = new StringBuilder();

		// generate the table header of the wiki page

		overviewPage.append("| ö ");
		for (final ChartWriter writer : CHART_WRITERS) {
			overviewPage.append("| ").append(writer.displayName).append(' ');
		}
		overviewPage.append("|\n");
		overviewPage.append(overviewPage.toString().replaceAll("[a-zA-Zö]", "-"));
		overviewPage.setCharAt(2, ' ');

		// generate images and fill the table

		for (final ChartGenerator generator : CHART_GENERATORS) {
			overviewPage.append("| ").append("[").append(generator.chartClass.getSimpleName())
					.append("](https://github.com/slothsoft/charts/blob/master/charts/src/main/java/")
					.append(generator.chartClass.getCanonicalName().replace('.', '/')).append(".java) ");
			for (final ChartWriter writer : CHART_WRITERS) {
				final Chart chart = generator.chartGenerator.get();
				final Path chartPath = targetFolder
						.resolve(generator.displayName.toLowerCase() + "-" + writer.displayName.toLowerCase() + ".png");
				writer.chartWriter.accept(chartPath, chart);
				System.out.println("Generated file: " + chartPath);
				overviewPage.append("| ![").append(generator.displayName).append("](examples/")
						.append(chartPath.getFileName()).append(") ");
			}
			overviewPage.append("|\n");
		}
		Files.write(targetFolder.resolve("Examples.md"), overviewPage.toString().getBytes());
	}
}

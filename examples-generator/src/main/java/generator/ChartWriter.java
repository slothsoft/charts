package generator;

import java.io.FileWriter;
import java.nio.file.Path;
import java.util.function.BiConsumer;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.Chart;
import de.slothsoft.charts.PaintInstructions;
import de.slothsoft.charts.swing.Graphics2DGraphicContext;
import de.slothsoft.charts.swt.SwtGraphicContext;
import swing.SwingImageExample;
import swt.SwtImageExample;

class ChartWriter {

	public static final ChartWriter[] ALL = {

			new ChartWriter("SWT", SwtGraphicContext.class, SwtImageExample::writeChartToFile),

			new ChartWriter("Swing", Graphics2DGraphicContext.class, SwingImageExample::writeChartToFile),

			new ChartWriter("SVG", Graphics2DGraphicContext.class, ChartWriter::writeChartToSvgFile).extension("svg"),

	};

	private static final void writeChartToSvgFile(Path imageFile, Chart chart) {
		final DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
		final Document document = domImpl.createDocument("http://www.w3.org/2000/svg", "svg", null);
		final SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
		chart.paintOn(new Graphics2DGraphicContext(svgGenerator), new PaintInstructions(new Area(400, 300)));

		try (FileWriter output = new FileWriter(imageFile.toFile())) {
			svgGenerator.stream(output, false);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	String displayName;
	Class<?> gcClass;
	BiConsumer<Path, Chart> chartWriter;
	String extension = "png";

	public ChartWriter(String displayName, Class<?> gcClass, BiConsumer<Path, Chart> chartWriter) {
		this.displayName = displayName;
		this.gcClass = gcClass;
		this.chartWriter = chartWriter;
	}

	public ChartWriter extension(String newExtension) {
		this.extension = newExtension;
		return this;
	}
}

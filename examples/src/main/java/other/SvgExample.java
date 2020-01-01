package other;
import java.awt.Desktop;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.Chart;
import de.slothsoft.charts.PaintInstructions;
import de.slothsoft.charts.swing.Graphics2DGraphicContext;
import linechart.LineChartExample;

/**
 * This example shows how to create SVG files from a {@link Chart}. Note that you need the
 * following Maven dependency (or an equivalent one in your build tool):
 *
 * <pre>
 * &lt;dependency>
 *     &lt;groupId>org.apache.xmlgraphics&lt;/groupId>
 *     &lt;artifactId>batik-svggen&lt;/artifactId>
 *     &lt;version>1.7&lt;/version>
 * &lt;/dependency>
 * &lt;dependency>
 *     &lt;groupId>org.apache.xmlgraphics
 *     &lt;/groupId>
 *     &lt;artifactId>batik-dom&lt;/artifactId>
 *     &lt;version>1.7&lt;/version>
 * &lt;/dependency>
 * </pre>
 *
 * @author Stef Schulz
 * @since 0.2.0
 */

public class SvgExample {

	public static void main(String[] args) throws IOException {
		// Create an instance of org.w3c.dom.Document
		final DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
		final Document document = domImpl.createDocument("http://www.w3.org/2000/svg", "svg", null);

		// SVGGraphics2D is just a Swing Graphics2D object
		final SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
		final Chart chart = LineChartExample.createChart();
		chart.paintOn(new Graphics2DGraphicContext(svgGenerator), new PaintInstructions(new Area(400, 300)));

		// Finally, stream SVG to file and open it
		final Path imageFile = Files.createTempFile(null, ".svg");
		try (FileWriter output = new FileWriter(imageFile.toFile())) {
			svgGenerator.stream(output, false);
		}
		Desktop.getDesktop().open(imageFile.toFile());
	}
}
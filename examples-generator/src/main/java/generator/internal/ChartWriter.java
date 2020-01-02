package generator.internal;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.function.BiConsumer;

import javax.imageio.ImageIO;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.SVGAbstractTranscoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.Chart;
import de.slothsoft.charts.PaintInstructions;
import de.slothsoft.charts.swing.Graphics2DGraphicContext;
import de.slothsoft.charts.swt.SwtGraphicContext;
import swing.SwingImageExample;
import swt.SwtImageExample;

public class ChartWriter {

	public static final ChartWriter[] ALL = {

			new ChartWriter("SWT", SwtGraphicContext.class, SwtImageExample::writeChartToFile),

			new ChartWriter("Swing", Graphics2DGraphicContext.class, SwingImageExample::writeChartToFile),

			new ChartWriter("SVG", Graphics2DGraphicContext.class, ChartWriter::writeChartToSvgFile) {

				@Override
				public String createImageMarkdown(Path chartPath) {
					final String folderName = chartPath.getParent().getFileName().toString();
					return "[![" + this.displayName + "](" + folderName + "/" + chartPath.getFileName() + ")]("
							+ folderName + "/" + chartPath.getFileName().toString().replace(".png", ".svg") + ")";
				}
			},

	};

	private static final void writeChartToSvgFile(Path imageFile, Chart chart) {
		// create SVG
		final DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
		final Document document = domImpl.createDocument("http://www.w3.org/2000/svg", "svg", null);
		final SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
		chart.paintOn(new Graphics2DGraphicContext(svgGenerator), new PaintInstructions(new Area(400, 300)));

		// github doesn't handle SVG well, so convert to PNG
		try (final ByteArrayOutputStream resultByteStream = new ByteArrayOutputStream()) {
			final StringWriter out = new StringWriter();
			svgGenerator.stream(out, false);

			final TranscoderInput transcoderInput = new TranscoderInput(new StringReader(out.getBuffer().toString()));
			final TranscoderOutput transcoderOutput = new TranscoderOutput(resultByteStream);

			final PNGTranscoder pngTranscoder = new PNGTranscoder();
			pngTranscoder.addTranscodingHint(SVGAbstractTranscoder.KEY_WIDTH, Float.valueOf(400f));
			pngTranscoder.addTranscodingHint(SVGAbstractTranscoder.KEY_HEIGHT, Float.valueOf(300f));
			pngTranscoder.transcode(transcoderInput, transcoderOutput);
			resultByteStream.flush();

			try (ByteArrayInputStream input = new ByteArrayInputStream(resultByteStream.toByteArray())) {
				final BufferedImage image = ImageIO.read(input);
				ImageIO.write(image, "PNG", imageFile.toFile());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public final String displayName;
	public final Class<?> gcClass;
	public final BiConsumer<Path, Chart> chartWriter;

	public ChartWriter(String displayName, Class<?> gcClass, BiConsumer<Path, Chart> chartWriter) {
		this.displayName = displayName;
		this.gcClass = gcClass;
		this.chartWriter = chartWriter;
	}

	public String createImageMarkdown(Path chartPath) {
		return "![" + this.displayName + "](" + chartPath.getParent().getFileName() + "/" + chartPath.getFileName()
				+ ")";
	}

}

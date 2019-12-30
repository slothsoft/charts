package generator;

import java.nio.file.Path;
import java.util.function.BiConsumer;

import de.slothsoft.charts.Chart;
import de.slothsoft.charts.swing.Graphics2DGraphicContext;
import de.slothsoft.charts.swt.SwtGraphicContext;
import swing.SwingImageExample;
import swt.SwtImageExample;

class ChartWriter {

	public static final ChartWriter[] ALL = {

			new ChartWriter("SWT", SwtGraphicContext.class, SwtImageExample::writeChartToFile),

			new ChartWriter("Swing", Graphics2DGraphicContext.class, SwingImageExample::writeChartToFile),

	};

	String displayName;
	Class<?> gcClass;
	BiConsumer<Path, Chart> chartWriter;

	public ChartWriter(String displayName, Class<?> gcClass, BiConsumer<Path, Chart> chartWriter) {
		this.displayName = displayName;
		this.gcClass = gcClass;
		this.chartWriter = chartWriter;
	}
}
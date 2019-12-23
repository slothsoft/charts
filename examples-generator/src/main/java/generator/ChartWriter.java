package generator;

import java.nio.file.Path;
import java.util.function.BiConsumer;

import de.slothsoft.charts.Chart;
import de.slothsoft.charts.swt.SwtGraphicContext;
import swt.SwtImageExample;

class ChartWriter {

	public static final ChartWriter[] ALL = {

			new ChartWriter("SWT", SwtGraphicContext.class, SwtImageExample::writeChartToFile),

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
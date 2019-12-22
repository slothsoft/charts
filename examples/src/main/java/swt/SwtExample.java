package swt;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.slothsoft.charts.Chart;
import de.slothsoft.charts.linechart.DataPointLine;
import de.slothsoft.charts.linechart.FunctionLine;
import de.slothsoft.charts.linechart.LineChart;
import de.slothsoft.charts.swt.ChartControl;

/**
 * This example displays a simple {@link ChartControl} with a {@link Chart} set on it.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class SwtExample {

	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());

		final LineChart chart = new LineChart();
		chart.addLine(new DataPointLine(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
		chart.addLine(new FunctionLine(x -> Math.sin(x)));

		final ChartControl chartControl = new ChartControl(shell, SWT.BORDER);
		chartControl.setModel(chart);
		chartControl.redraw();

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}

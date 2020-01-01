package swt;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.slothsoft.charts.barchart.BarChart;
import de.slothsoft.charts.swt.ChartControl;

/**
 * This example displays a simple {@link ChartControl} with a {@link BarChart} set on it.
 *
 * @author Stef Schulz
 * @since 0.2.0
 */

public class SwtBarChartExample {

	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());

		final BarChart chart = new BarChart();
		chart.getTitle().setText("Bar Chart");
		chart.addBars(13, 5, 10, 15);

		final ChartControl chartControl = new ChartControl(shell, SWT.BORDER);
		chartControl.setModel(chart);

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

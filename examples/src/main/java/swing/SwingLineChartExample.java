package swing;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import de.slothsoft.charts.linechart.DataPointLine;
import de.slothsoft.charts.linechart.FunctionLine;
import de.slothsoft.charts.linechart.LineChart;
import de.slothsoft.charts.swing.ChartControl;
import de.slothsoft.charts.swing.MoveLineChartByMouseListener;

/**
 * This example displays a simple {@link ChartControl} with a {@link LineChart} set on it.
 *
 * @author Stef Schulz
 * @since 0.2.0
 */

public class SwingLineChartExample {

	public static void main(String[] args) {
		final LineChart chart = new LineChart();
		chart.getTitle().setText("Line Chart");
		chart.addLine(new DataPointLine(-3, -2, -1, 0, 1, 2, 2.5, 2.75, 2.82, 2.91, 2.95));
		chart.addLine(new FunctionLine(x -> Math.cos(x)).color(0xFF00FFFF));

		final ChartControl chartControl = new ChartControl(chart);
		// hook some additional features to the control
		MoveLineChartByMouseListener.hookToControl(chartControl, chart);

		final JFrame frame = new JFrame("Line Chart");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(chartControl, BorderLayout.CENTER);
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}

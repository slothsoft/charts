package swing;
import java.awt.BorderLayout;

import javax.swing.JFrame;

import de.slothsoft.charts.barchart.BarChart;
import de.slothsoft.charts.swing.ChartControl;

/**
 * This example displays a simple {@link ChartControl} with a {@link BarChart} set on it.
 *
 * @author Stef Schulz
 * @since 0.2.0
 */

public class SwingBarChartExample {

	public static void main(String[] args) {
		final BarChart chart = new BarChart();
		chart.getTitle().setText("Bar Chart");
		chart.addBars(13, 5, 10, 15);

		final JFrame frame = new JFrame("Bar Chart");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new ChartControl(chart), BorderLayout.CENTER);
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}

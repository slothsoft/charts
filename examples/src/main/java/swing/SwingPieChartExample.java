package swing;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import de.slothsoft.charts.piechart.PieChart;
import de.slothsoft.charts.swing.ChartControl;

/**
 * This example displays a simple {@link ChartControl} with a {@link PieChart} set on it.
 *
 * @author Stef Schulz
 * @since 0.2.0
 */

public class SwingPieChartExample {

	public static void main(String[] args) {
		final PieChart chart = new PieChart();
		chart.getTitle().setText("Pie Chart");
		chart.addSlices(1, 3, 5, 15);

		final JFrame frame = new JFrame("Pie Chart");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new ChartControl(chart), BorderLayout.CENTER);
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}

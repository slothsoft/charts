package swing;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

public class SwingClickListenerExample {

	public static void main(String[] args) {
		final LineChart chart = new LineChart();
		chart.addLine(new DataPointLine(-3, -2, -1, 0, 1, 2, 2.5, 2.75, 2.82, 2.91, 2.95));
		chart.addLine(new FunctionLine(x -> Math.cos(x)).color(0xFF00FFFF));

		final ChartControl chartControl = new ChartControl(chart);
		MoveLineChartByMouseListener.hookToControl(chartControl, chart);

		// add the click listener

		chartControl.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				final double[] graphXY = chart.convertToGraphCoordinates(e.getX(), e.getY());
				System.out.println("You clicked on " + graphXY[0] + "|" + graphXY[1]);
			}
		});

		final JFrame frame = new JFrame("Line Chart");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(chartControl, BorderLayout.CENTER);
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}

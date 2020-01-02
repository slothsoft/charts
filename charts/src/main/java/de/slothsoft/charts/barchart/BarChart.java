package de.slothsoft.charts.barchart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.Chart;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;
import de.slothsoft.charts.RefreshListener;

/**
 * This class represents a {@link Chart} that displays some bars to compare values. It's
 * structure looks like this:<br>
 * <img src=
 * "https://raw.githubusercontent.com/wiki/slothsoft/charts/images/bar-chart-structure.png"
 * alt="structure">
 *
 * @author Stef Schulz
 * @since 0.2.0
 */

public class BarChart extends Chart {

	final XYAxis axis = new XYAxis(this);
	final List<Bar> bars = new ArrayList<>();
	final RefreshListener refreshListener = e -> fireRefreshNeeded();

	int defaultBarColor = 0xFF22AA22;

	/**
	 * Default constructor.
	 */

	public BarChart() {
		addChartPart(this.axis);
	}

	@Override
	protected void paintGraph(GraphicContext gc, PaintInstructions instructions) {
		final Area area = instructions.getArea();

		final double width = area.calculateWidth();
		final double height = area.calculateHeight();
		final double startX = area.getStartX();

		final double maxValue = this.bars.stream().mapToDouble(Bar::getValue).max().orElse(1);
		final double widthPerBar = width / (2 * (this.bars.size() + 1));

		int index = 0;
		for (final Bar bar : this.bars) {
			gc.setColor(bar.color);
			final double barHeight = (bar.getValue()) / maxValue * height;

			gc.fillRectangle(startX + widthPerBar + 2 * index * widthPerBar, height - barHeight, widthPerBar,
					barHeight + 1);
			index++;
		}
	}

	/**
	 * Creates a new {@link Bar} and adds it to this chart.
	 *
	 * @param value the value of the bar
	 * @return the added bar
	 */

	public Bar addBar(double value) {
		final Bar result = doAddBar(value);
		fireRefreshNeeded();
		return result;
	}

	private Bar doAddBar(double value) {
		final Bar bar = new Bar(value).color(this.defaultBarColor);
		bar.addRefreshListener(this.refreshListener);
		this.bars.add(bar);
		return bar;
	}

	/**
	 * Creates some new {@link Bar}s and adds them to this chart.
	 *
	 * @param values the values of the bars
	 * @return the added bars
	 */

	public Bar[] addBars(double... values) {
		final Bar[] result = new Bar[values.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = doAddBar(values[i]);
		}
		fireRefreshNeeded();
		return result;
	}

	/**
	 * Removes a {@link Bar} from this chart.
	 *
	 * @param bar the removed bar
	 */

	public void removeBar(Bar bar) {
		doRemoveBar(bar);
		fireRefreshNeeded();
	}

	private void doRemoveBar(Bar bar) {
		bar.removeRefreshListener(this.refreshListener);
		this.bars.remove(bar);
	}

	/**
	 * Removes some {@link Bar}s from this chart.
	 *
	 * @param removedBars the removed bars
	 */

	public void removeBars(Bar... removedBars) {
		Arrays.stream(removedBars).forEach(this::doRemoveBar);
		fireRefreshNeeded();
	}

	/**
	 * Returns the default color of the bars as ARGB int, e.g. red is
	 * <code>0xFFFF0000</code> and blue is <code>0xFF0000FF</code>.
	 *
	 * @return the color
	 */

	public int getDefaultBarColor() {
		return this.defaultBarColor;
	}

	/**
	 * Sets the default color of the bars as ARGB int, e.g. red is <code>0xFFFF0000</code>
	 * and blue is <code>0xFF0000FF</code>.
	 *
	 * @param newDefaultBarColor the color
	 * @return this instance
	 */

	public Chart defaultBarColor(int newDefaultBarColor) {
		setDefaultBarColor(newDefaultBarColor);
		return this;
	}

	/**
	 * Sets the default color of the bars as ARGB int, e.g. red is <code>0xFFFF0000</code>
	 * and blue is <code>0xFF0000FF</code>.
	 *
	 * @param defaultBarColor the color
	 */

	public void setDefaultBarColor(int defaultBarColor) {
		final int oldDefaultBarColor = this.defaultBarColor;
		this.defaultBarColor = defaultBarColor;
		if (oldDefaultBarColor != this.defaultBarColor) {
			fireRefreshNeeded();
		}
	}

	/**
	 * Returns the axis of this line chart.
	 *
	 * @return the axis
	 */

	public XYAxis getAxis() {
		return this.axis;
	}
}

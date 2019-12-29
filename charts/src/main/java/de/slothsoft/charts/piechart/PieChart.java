package de.slothsoft.charts.piechart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.Chart;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;
import de.slothsoft.charts.RefreshListener;

/**
 * This class represents a {@link Chart} that displays a circle with sections representing
 * percentages. It's structure looks like this:<br>
 * <img src=
 * "https://raw.githubusercontent.com/wiki/slothsoft/charts/images/pie-chart-structure.png"
 * alt="structure">
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class PieChart extends Chart {

	private static final int DEGREE = 360;
	private static final int[] SLICE_COLORS = {0xFFf5b5fc, 0xFF96f7d2, 0xFFf0f696, 0xFFfcb1b1};

	final List<PieSlice> slices = new ArrayList<>();
	final RefreshListener refreshListener = e -> fireRefreshNeeded();

	int pieColor = 0xFF222222;
	double startAngle;
	double pieBorder = 2;

	@Override
	protected void paintGraph(GraphicContext gc, PaintInstructions instructions) {
		final Area area = instructions.getArea();

		double widthAndHeight = Math.min(area.getEndX() - area.getStartX(), area.getEndY() - area.getStartY());
		double startX = area.getStartX() + (area.getEndX() - area.getStartX() - widthAndHeight) / 2;
		double startY = area.getStartY() + (area.getEndY() - area.getStartY() - widthAndHeight) / 2;

		gc.setColor(this.pieColor);
		gc.fillOval(startX, startY, widthAndHeight, widthAndHeight);

		startX += this.pieBorder;
		startY += this.pieBorder;
		widthAndHeight -= 2 * this.pieBorder;

		final double valueSum = this.slices.stream().mapToDouble(PieSlice::getValue).sum();
		double currentAngle = this.startAngle;

		for (final PieSlice slice : this.slices) {
			final double sliceAngle = slice.getValue() / valueSum * DEGREE;
			gc.setColor(slice.color);
			gc.fillArc(startX, startY, widthAndHeight, widthAndHeight, currentAngle, sliceAngle);
			currentAngle += sliceAngle;
		}
	}

	/**
	 * Creates a new {@link PieSlice} and adds it to this chart.
	 *
	 * @param value the value of the slice
	 * @return the added slice
	 */

	public PieSlice addSlice(double value) {
		final PieSlice result = doAddSlice(value);
		fireRefreshNeeded();
		return result;
	}

	private PieSlice doAddSlice(double value) {
		final PieSlice slice = new PieSlice(value).color(SLICE_COLORS[this.slices.size() % SLICE_COLORS.length]);
		slice.addRefreshListener(this.refreshListener);
		this.slices.add(slice);
		return slice;
	}

	/**
	 * Creates some new {@link PieSlice}s and adds them to this chart.
	 *
	 * @param values the values of the slices
	 * @return the added slices
	 */

	public PieSlice[] addSlices(double... values) {
		final PieSlice[] result = new PieSlice[values.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = doAddSlice(values[i]);
		}
		fireRefreshNeeded();
		return result;
	}

	/**
	 * Removes a {@link PieSlice} from this chart.
	 *
	 * @param slice the removed slice
	 */

	public void removeSlice(PieSlice slice) {
		doRemoveSlice(slice);
		fireRefreshNeeded();
	}

	private void doRemoveSlice(PieSlice slice) {
		slice.removeRefreshListener(this.refreshListener);
		this.slices.remove(slice);
	}

	/**
	 * Removes some {@link PieSlice}s from this chart.
	 *
	 * @param removedSlices the removed slices
	 */

	public void removeSlices(PieSlice... removedSlices) {
		Arrays.stream(removedSlices).forEach(this::doRemoveSlice);
		fireRefreshNeeded();
	}

	/**
	 * Returns the color of the pie as ARGB int, e.g. red is <code>0xFFFF0000</code> and
	 * blue is <code>0xFF0000FF</code>.
	 *
	 * @return the color
	 */

	public int getPieColor() {
		return this.pieColor;
	}

	/**
	 * Sets the color of the pie as ARGB int, e.g. red is <code>0xFFFF0000</code> and blue
	 * is <code>0xFF0000FF</code>.
	 *
	 * @param newPieColor the color
	 * @return this instance
	 */

	public Chart pieColor(int newPieColor) {
		setPieColor(newPieColor);
		return this;
	}

	/**
	 * Sets the color of the pie as ARGB int, e.g. red is <code>0xFFFF0000</code> and blue
	 * is <code>0xFF0000FF</code>.
	 *
	 * @param pieColor the color
	 */

	public void setPieColor(int pieColor) {
		final int oldPieColor = this.pieColor;
		this.pieColor = pieColor;
		if (oldPieColor != this.pieColor) {
			fireRefreshNeeded();
		}
	}

	/**
	 * Returns the pie's border size. The border is drawn in the {@link #getPieColor()}.
	 *
	 * @return the border's size
	 * @see #getPieColor()
	 */

	public double getPieBorder() {
		return this.pieBorder;
	}

	/**
	 * Sets the pie's border size. The border is drawn in the {@link #getPieColor()}.
	 *
	 * @param newPieBorder the border's size
	 * @return this instance
	 * @see #getPieColor()
	 */

	public PieChart pieBorder(double newPieBorder) {
		setPieBorder(newPieBorder);
		return this;
	}

	/**
	 * Sets the pie's border size. The border is drawn in the {@link #getPieColor()}.
	 *
	 * @param pieBorder the border's size
	 * @see #getPieColor()
	 */

	public void setPieBorder(double pieBorder) {
		final double oldPieBorder = this.pieBorder;
		this.pieBorder = pieBorder;
		if (oldPieBorder != pieBorder) {
			fireRefreshNeeded();
		}
	}

	/**
	 * Returns the angle the pie starts drawing its slices. Angles are interpreted such
	 * that 0&nbsp;degrees is at the 3&nbsp;o'clock position. A positive value indicates a
	 * counter-clockwise rotation while a negative value indicates a clockwise rotation.
	 *
	 * @return the start angle
	 */

	public double getStartAngle() {
		return this.startAngle;
	}

	/**
	 * Sets the angle the pie starts drawing its slices. Angles are interpreted such that
	 * 0&nbsp;degrees is at the 3&nbsp;o'clock position. A positive value indicates a
	 * counter-clockwise rotation while a negative value indicates a clockwise rotation.
	 *
	 * @param newStartAngle the start angle
	 * @return this instance
	 */

	public PieChart startAngle(double newStartAngle) {
		setStartAngle(newStartAngle);
		return this;
	}

	/**
	 * Sets the angle the pie starts drawing its slices. Angles are interpreted such that
	 * 0&nbsp;degrees is at the 3&nbsp;o'clock position. A positive value indicates a
	 * counter-clockwise rotation while a negative value indicates a clockwise rotation.
	 *
	 * @param startAngle the start angle
	 */

	public void setStartAngle(double startAngle) {
		final double oldStartAngle = this.startAngle;
		this.startAngle = startAngle;
		if (oldStartAngle != startAngle) {
			fireRefreshNeeded();
		}
	}

}

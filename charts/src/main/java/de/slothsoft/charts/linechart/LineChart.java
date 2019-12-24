package de.slothsoft.charts.linechart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.Chart;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;

/**
 * This class represents a {@link Chart} that displays lines of some sort inside a
 * coordinate system. It's structure looks like this: <br>
 * <img src=
 * "https://raw.githubusercontent.com/wiki/slothsoft/charts/images/line-chart-structure.png"
 * alt="structure">
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class LineChart extends Chart {

	final List<Line> lines = new ArrayList<>();

	private Area lastGraphArea;
	private Area displayedArea;

	@Override
	protected void paintGraph(GraphicContext gc, PaintInstructions instructions) {
		this.lastGraphArea = instructions.getArea();

		final Area graphArea = calculateDisplayedArea();
		final double graphWidth = graphArea.getEndX() - graphArea.getStartX();
		final double graphHeight = graphArea.getEndY() - graphArea.getStartY();

		final double actualWidth = this.lastGraphArea.getEndX() - this.lastGraphArea.getStartX();
		final double actualHeight = this.lastGraphArea.getEndY() - this.lastGraphArea.getStartY();
		final double scaleX = actualWidth / graphWidth;
		final double scaleY = actualHeight / graphHeight;
		gc.scale(scaleX, scaleY);

		final PaintInstructions lineInstructions = instructions.copy().area(graphArea);
		final GraphicContext linesGc = new FlipYGraphicContext(gc);
		// the top left corner is not where the graph origin is, so move
		final double originX = -Math.min(graphArea.getStartX(), graphArea.getEndX()) * scaleX;
		final double originY = Math.max(graphArea.getStartY(), graphArea.getEndY()) * scaleY;
		gc.translate(originX, originY);

		for (final Line line : this.lines) {
			line.paintOn(linesGc, lineInstructions);
		}

		// reset everything that was done previously
		gc.translate(-originX, -originY);
		gc.scale(1 / scaleX, 1 / scaleY);
	}

	/**
	 * Calculates the displayed area via {@link #getDisplayedArea()} or the added lines.
	 *
	 * @return the displayed area
	 * @see #getDisplayedArea()
	 * @see #addLine(Line)
	 * @see #addLines(Line[])
	 */

	Area calculateDisplayedArea() {
		if (this.displayedArea != null) return this.displayedArea;
		if (this.lines.isEmpty()) return Line.createDefaultArea();

		Area result = new Area();
		for (final Line line : this.lines) {
			result = result.unite(line.calculatePreferredArea());
		}
		return result;
	}

	/**
	 * Adds a line to the chart.
	 *
	 * @param line a line to add
	 */

	public void addLine(Line line) {
		this.lines.add(line);
		fireRefreshNeeded();
	}

	/**
	 * Adds some lines to the chart.
	 *
	 * @param addedLines lines to add
	 */

	public void addLines(Line... addedLines) {
		this.lines.addAll(Arrays.asList(addedLines));
		fireRefreshNeeded();
	}

	/**
	 * Removes a line from the chart.
	 *
	 * @param line a line to add
	 */

	public void removeLine(Line line) {
		this.lines.remove(line);
		fireRefreshNeeded();
	}

	/**
	 * Removes some lines to the chart.
	 *
	 * @param removedLines lines to add
	 */

	public void removeLines(Line... removedLines) {
		this.lines.removeAll(Arrays.asList(removedLines));
		fireRefreshNeeded();
	}

	/**
	 * Returns the displayed area of this chart, i.e. the coordinates to display.
	 * <code>null</code> is used to indicate the value is calculated by questioning the
	 * {@link Line}s.
	 *
	 * @return the displayed area
	 */

	public Area getDisplayedArea() {
		return this.displayedArea;
	}

	/**
	 * Sets the displayed area of this chart, i.e. the coordinates to display.
	 * <code>null</code> is used to indicate the value is calculated by questioning the
	 * {@link Line}s.
	 *
	 * @param newDisplayedArea the displayed area
	 * @return this instance
	 */

	public LineChart displayedArea(Area newDisplayedArea) {
		setDisplayedArea(newDisplayedArea);
		return this;
	}

	/**
	 * Sets the displayed area of this chart, i.e. the coordinates to display.
	 * <code>null</code> is used to indicate the value is calculated by questioning the
	 * {@link Line}s.
	 *
	 * @param displayedArea the displayed area
	 */

	public void setDisplayedArea(Area displayedArea) {
		final Area oldDisplayedArea = this.displayedArea;
		this.displayedArea = displayedArea;
		if (!Objects.equals(displayedArea, oldDisplayedArea)) {
			fireRefreshNeeded();
		}
	}

	/**
	 * Moves the displayed area of this {@link Chart} by the coordinates used for the
	 * entire chart. Let's say the chart is painted on an area 1000x1000 pixels, but the
	 * graph only displays something between the coordinates 0 and 1. If you move
	 * 100pixels in the chart scale, you only need to move the graph 0.1 points.
	 *
	 * @param xIncrement the x movement
	 * @param yIncrement the y movement
	 */

	public void moveDisplayedAreaByChartCoordinates(double xIncrement, double yIncrement) {
		if (this.lastGraphArea == null)
			throw new IllegalArgumentException(
					"You need to paint the graph at least once before you can move it with this method!");
		final Area wantedArea = calculateDisplayedArea();

		final double scaleX = (this.lastGraphArea.getEndX() - this.lastGraphArea.getStartX())
				/ (wantedArea.getEndX() - wantedArea.getStartX());
		final double scaleY = (this.lastGraphArea.getEndY() - this.lastGraphArea.getStartY())
				/ (wantedArea.getEndY() - wantedArea.getStartY());

		moveDisplayedArea(xIncrement / scaleX, yIncrement / scaleY);
	}

	/**
	 * Moves the displayed area of this {@link Chart} directly, i.e. adds the movement
	 * coordinates to the already existing ones.
	 *
	 * @param xIncrement the x movement
	 * @param yIncrement the y movement
	 */

	public void moveDisplayedArea(double xIncrement, double yIncrement) {
		if (xIncrement == 0 && yIncrement == 0) return;
		final Area newArea = calculateDisplayedArea().copy();
		newArea.move(xIncrement, yIncrement);
		setDisplayedArea(newArea);
	}

}

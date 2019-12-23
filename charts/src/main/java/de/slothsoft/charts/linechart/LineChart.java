package de.slothsoft.charts.linechart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	private Area displayedArea;

	@Override
	protected void paintGraph(GraphicContext gc, PaintInstructions instructions) {
		final Area rect = instructions.getArea();

		final Area graphArea = calculateDisplayedArea();
		final double graphWidth = graphArea.getEndX() - graphArea.getStartX();
		final double graphHeight = graphArea.getEndY() - graphArea.getStartY();

		final double actualWidth = rect.getEndX() - rect.getStartX();
		final double actualHeight = rect.getEndY() - rect.getStartY();
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

	Area calculateDisplayedArea() {
		if (this.displayedArea != null) {
			return this.displayedArea;
		}
		if (this.lines.isEmpty()) {
			return Line.createDefaultArea();
		}

		Area result = new Area();
		for (final Line line : this.lines) {
			result = result.unite(line.calculatePreferredArea());
		}
		return result;
	}

	/**
	 * Adds a line to the chart.
	 *
	 * @param line - a line to add
	 */

	public void addLine(Line line) {
		this.lines.add(line);
	}

	/**
	 * Adds some lines to the chart.
	 *
	 * @param addedLines - lines to add
	 */

	public void addLines(Line... addedLines) {
		this.lines.addAll(Arrays.asList(addedLines));
	}

	/**
	 * Removes a line from the chart.
	 *
	 * @param line - a line to add
	 */

	public void removeLine(Line line) {
		this.lines.remove(line);
	}

	/**
	 * Removes some lines to the chart.
	 *
	 * @param removedLines - lines to add
	 */

	public void removeLines(Line... removedLines) {
		this.lines.removeAll(Arrays.asList(removedLines));
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
	 * @param newDisplayedArea - the displayed area
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
	 * @param displayedArea - the displayed area
	 */

	public void setDisplayedArea(Area displayedArea) {
		this.displayedArea = displayedArea;
	}

}

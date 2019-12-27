package de.slothsoft.charts.linechart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.Chart;
import de.slothsoft.charts.ChartPart;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;
import de.slothsoft.charts.RefreshListener;

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

	Area lastGraphArea;

	private final XAxis xAxis = new XAxis(this);
	private final YAxis yAxis = new YAxis(this);
	private final RefreshListener refreshListener = e -> fireRefreshNeeded();

	private Area displayedArea;
	private double zoomFactor = 0.25;

	public LineChart() {
		addChartPart(this.xAxis);
		addChartPart(this.yAxis);
	}

	@Override
	public void paintOn(GraphicContext gc, PaintInstructions instructions) {
		this.lastGraphArea = instructions.getArea().copy();
		for (final ChartPart part : fetchChartParts()) {
			this.lastGraphArea = part.snipNecessarySpace(this.lastGraphArea);
		}
		super.paintOn(gc, instructions);
	}

	@Override
	protected void paintGraph(GraphicContext gc, PaintInstructions instructions) {
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
		line.addRefreshListener(this.refreshListener);
		this.lines.add(line);
		fireRefreshNeeded();
	}

	/**
	 * Adds some lines to the chart.
	 *
	 * @param addedLines lines to add
	 */

	public void addLines(Line... addedLines) {
		for (final Line addedLine : addedLines) {
			addedLine.addRefreshListener(this.refreshListener);
		}
		this.lines.addAll(Arrays.asList(addedLines));
		fireRefreshNeeded();
	}

	/**
	 * Removes a line from the chart.
	 *
	 * @param line a line to add
	 */

	public void removeLine(Line line) {
		line.removeRefreshListener(this.refreshListener);
		this.lines.remove(line);
		fireRefreshNeeded();
	}

	/**
	 * Removes some lines to the chart.
	 *
	 * @param removedLines lines to add
	 */

	public void removeLines(Line... removedLines) {
		for (final Line removedLine : removedLines) {
			removedLine.removeRefreshListener(this.refreshListener);
		}
		this.lines.removeAll(Arrays.asList(removedLines));
		fireRefreshNeeded();
	}

	/**
	 * Moves the displayed area of this {@link Chart} by the coordinates used for the
	 * entire chart. Let's say the chart is painted on an area 1000x1000 pixels, but the
	 * graph only displays something between the coordinates 0 and 1. If you move
	 * 100pixels in the chart scale, you only need to move the graph 0.1 points.
	 *
	 * @param xIncrement the x movement
	 * @param yIncrement the y movement
	 * @exception IllegalArgumentException if graph was never painted before
	 */

	public void moveDisplayedAreaByChartCoordinates(double xIncrement, double yIncrement) {
		requireLastGraphAreaNotNull();
		final Area wantedArea = calculateDisplayedArea();

		final double scaleX = (this.lastGraphArea.getEndX() - this.lastGraphArea.getStartX())
				/ (wantedArea.getEndX() - wantedArea.getStartX());
		final double scaleY = (this.lastGraphArea.getEndY() - this.lastGraphArea.getStartY())
				/ (wantedArea.getEndY() - wantedArea.getStartY());

		moveDisplayedAreaDirectly(xIncrement / scaleX, yIncrement / scaleY);
	}

	private void requireLastGraphAreaNotNull() {
		if (this.lastGraphArea == null)
			throw new IllegalArgumentException(
					"You need to paint the graph at least once before you can move it with this method!");
	}

	/**
	 * Moves the displayed area of this {@link Chart} directly, i.e. adds the movement
	 * coordinates to the already existing ones.
	 *
	 * @param xIncrement the x movement
	 * @param yIncrement the y movement
	 * @see #moveDisplayedAreaByChartCoordinates(double, double)
	 */

	void moveDisplayedAreaDirectly(double xIncrement, double yIncrement) {
		if (xIncrement == 0 && yIncrement == 0) return;
		final Area newArea = calculateDisplayedArea().copy();
		newArea.move(xIncrement, yIncrement);
		setDisplayedArea(newArea);
	}

	/**
	 * Zooms the graph area in using the chart's coordinates.
	 *
	 * @param chartX the x coordinate in the chart's coordinate system
	 * @param chartY the y coordinate in the chart's coordinate system
	 * @see #convertToGraphCoordinates(double, double)
	 * @see #convertToGraphX(double)
	 * @see #convertToGraphY(double)
	 * @see #zoomDisplayedAreaOutByChartCoordinates(double, double)
	 */

	public void zoomDisplayedAreaInByChartCoordinates(double chartX, double chartY) {
		zoomDisplayedAreaInByGraphCoordinates(convertToGraphX(chartX), convertToGraphY(chartY));
	}

	/**
	 * Zooms the graph area in using the graph's coordinates.
	 *
	 * @param graphX the x coordinate in the graph's coordinate system
	 * @param graphY the y coordinate in the graph's coordinate system
	 * @see #convertToGraphCoordinates(double, double)
	 * @see #convertToGraphX(double)
	 * @see #convertToGraphY(double)
	 * @see #zoomDisplayedAreaOutByGraphCoordinates(double, double)
	 */

	public void zoomDisplayedAreaInByGraphCoordinates(double graphX, double graphY) {
		zoom(graphX, graphY, true);
	}

	private void zoom(double graphX, double graphY, boolean in) {
		final Area wantedArea = calculateDisplayedArea();

		final double width = wantedArea.getEndX() - wantedArea.getStartX();
		final double height = wantedArea.getEndY() - wantedArea.getStartY();

		final double beforeXScale = (graphX - wantedArea.getStartX()) / width;
		final double beforeYScale = (graphY - wantedArea.getStartY()) / height;

		double beforeXZoom = beforeXScale * this.zoomFactor * width;
		double afterXZoom = (1 - beforeXScale) * this.zoomFactor * width;

		double beforeYZoom = beforeYScale * this.zoomFactor * height;
		double afterYZoom = (1 - beforeYScale) * this.zoomFactor * height;

		if (!in) {
			beforeXZoom *= -1;
			afterXZoom *= -1;
			beforeYZoom *= -1;
			afterYZoom *= -1;
		}
		setDisplayedArea(new Area(wantedArea.getStartX() + beforeXZoom, wantedArea.getStartY() + beforeYZoom,
				wantedArea.getEndX() - afterXZoom, wantedArea.getEndY() - afterYZoom));
	}

	/**
	 * Zooms the graph area out using the chart's coordinates.
	 *
	 * @param chartX the x coordinate in the chart's coordinate system
	 * @param chartY the y coordinate in the chart's coordinate system
	 * @exception IllegalArgumentException if graph was never painted before
	 * @see #convertToGraphCoordinates(double, double)
	 * @see #convertToGraphX(double)
	 * @see #convertToGraphY(double)
	 * @see #zoomDisplayedAreaOutByChartCoordinates(double, double)
	 */

	public void zoomDisplayedAreaOutByChartCoordinates(double chartX, double chartY) {
		zoomDisplayedAreaOutByGraphCoordinates(convertToGraphX(chartX), convertToGraphY(chartY));
	}

	/**
	 * Zooms the graph area out using the graph's coordinates.
	 *
	 * @param graphX the x coordinate in the graph's coordinate system
	 * @param graphY the y coordinate in the graph's coordinate system
	 * @exception IllegalArgumentException if graph was never painted before
	 * @see #convertToGraphCoordinates(double, double)
	 * @see #convertToGraphX(double)
	 * @see #convertToGraphY(double)
	 * @see #zoomDisplayedAreaInByGraphCoordinates(double, double)
	 */

	public void zoomDisplayedAreaOutByGraphCoordinates(double graphX, double graphY) {
		zoom(graphX, graphY, false);
	}

	/**
	 * Converts chart coordinates to a graph ones. The chart is everything and x and y
	 * move from the top left to the bottom right. The graph is the area with the lines
	 * and moves from bottom left to top right.
	 *
	 * @param chartX the x coordinate in the chart's coordinate system
	 * @param chartY the y coordinate in the chart's coordinate system
	 * @exception IllegalArgumentException if graph was never painted before
	 * @see #convertToGraphX(double)
	 * @see #convertToGraphY(double)
	 */

	public double[] convertToGraphCoordinates(double chartX, double chartY) {
		return new double[]{convertToGraphX(chartX), convertToGraphY(chartY)};
	}

	/**
	 * Converts a chart coordinate to a graph one. The chart is everything and x and y
	 * move from the top left to the bottom right. The graph is the area with the lines
	 * and moves from bottom left to top right.
	 *
	 * @param chartX the x coordinate in the chart's coordinate system
	 * @exception IllegalArgumentException if graph was never painted before
	 * @see #convertToGraphCoordinates(double, double)
	 * @see #convertToGraphY(double)
	 */

	public double convertToGraphX(double chartX) {
		requireLastGraphAreaNotNull();
		final Area wantedArea = calculateDisplayedArea();

		final double scale = (wantedArea.getEndX() - wantedArea.getStartX())
				/ (this.lastGraphArea.getEndX() - this.lastGraphArea.getStartX());
		return scale * (chartX - this.lastGraphArea.getStartX()) + wantedArea.getStartX();
	}

	/**
	 * Converts a chart coordinate to a graph one. The chart is everything and x and y
	 * move from the top left to the bottom right. The graph is the area with the lines
	 * and moves from bottom left to top right.
	 *
	 * @param chartY the y coordinate in the chart's coordinate system
	 * @exception IllegalArgumentException if graph was never painted before
	 * @see #convertToGraphCoordinates(double, double)
	 * @see #convertToGraphX(double)
	 */

	public double convertToGraphY(double chartY) {
		requireLastGraphAreaNotNull();
		final Area wantedArea = calculateDisplayedArea();

		final double scale = (wantedArea.getEndY() - wantedArea.getStartY())
				/ (this.lastGraphArea.getEndY() - this.lastGraphArea.getStartY());
		return wantedArea.getEndY() - scale * (chartY - this.lastGraphArea.getStartY());
	}

	/**
	 * Converts a graph coordinate to a chart one. The chart is everything and x and y
	 * move from the top left to the bottom right. The graph is the area with the lines
	 * and moves from bottom left to top right.
	 *
	 * @param graphX the x coordinate in the graph's coordinate system
	 * @exception IllegalArgumentException if graph was never painted before
	 * @see #convertToChartY(double)
	 */

	public double convertToChartX(double graphX) {
		requireLastGraphAreaNotNull();
		final Area wantedArea = calculateDisplayedArea();

		final double scale = (wantedArea.getEndX() - wantedArea.getStartX())
				/ (this.lastGraphArea.getEndX() - this.lastGraphArea.getStartX());
		return (graphX - wantedArea.getStartX()) / scale + this.lastGraphArea.getStartX();
	}

	/**
	 * Converts a graph coordinate to a chart one. The chart is everything and x and y
	 * move from the top left to the bottom right. The graph is the area with the lines
	 * and moves from bottom left to top right.
	 *
	 * @param graphY the y coordinate in the graph's coordinate system
	 * @exception IllegalArgumentException if graph was never painted before
	 * @see #convertToChartX(double)
	 */

	public double convertToChartY(double graphY) {
		requireLastGraphAreaNotNull();
		final Area wantedArea = calculateDisplayedArea();

		final double scale = (wantedArea.getEndY() - wantedArea.getStartY())
				/ (this.lastGraphArea.getEndY() - this.lastGraphArea.getStartY());
		return (-graphY + wantedArea.getEndY()) / scale + this.lastGraphArea.getStartY();
	}

	/**
	 * Resets the displayed area.
	 *
	 * @see #setDisplayedArea(Area)
	 */

	public void resetDisplayedArea() {
		setDisplayedArea(null);
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
	 * Returns the factor which should be used to zoom the graph area. A value of 0.25
	 * means it gets zoomed by 25%.
	 *
	 * @return the zoom factor
	 * @see #zoomDisplayedAreaInByChartCoordinates(double, double)
	 * @see #zoomDisplayedAreaInByGraphCoordinates(double, double)
	 * @see #zoomDisplayedAreaOutByChartCoordinates(double, double)
	 * @see #zoomDisplayedAreaOutByGraphCoordinates(double, double)
	 */

	public double getZoomFactor() {
		return this.zoomFactor;
	}

	/**
	 * Sets the factor which should be used to zoom the graph area. A value of 0.25 means
	 * it gets zoomed by 25%.
	 *
	 * @param newZoomFactor the zoom factor
	 * @return this instance
	 * @see #zoomDisplayedAreaInByChartCoordinates(double, double)
	 * @see #zoomDisplayedAreaInByGraphCoordinates(double, double)
	 * @see #zoomDisplayedAreaOutByChartCoordinates(double, double)
	 * @see #zoomDisplayedAreaOutByGraphCoordinates(double, double)
	 */

	public LineChart zoomFactor(double newZoomFactor) {
		setZoomFactor(newZoomFactor);
		return this;
	}

	/**
	 * Sets the factor which should be used to zoom the graph area. A value of 0.25 means
	 * it gets zoomed by 25%.
	 *
	 * @param zoomFactor the zoom factor
	 * @see #zoomDisplayedAreaInByChartCoordinates(double, double)
	 * @see #zoomDisplayedAreaInByGraphCoordinates(double, double)
	 * @see #zoomDisplayedAreaOutByChartCoordinates(double, double)
	 * @see #zoomDisplayedAreaOutByGraphCoordinates(double, double)
	 */

	public void setZoomFactor(double zoomFactor) {
		this.zoomFactor = zoomFactor;
	}

	/**
	 * Returns the x axis of this line chart.
	 *
	 * @return the x axis
	 */

	public XAxis getXAxis() {
		return this.xAxis;
	}

	/**
	 * Returns the y axis of this line chart.
	 *
	 * @return the y axis
	 */

	public YAxis getYAxis() {
		return this.yAxis;
	}
}

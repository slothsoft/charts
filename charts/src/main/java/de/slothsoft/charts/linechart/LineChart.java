package de.slothsoft.charts.linechart;

import java.util.ArrayList;
import java.util.List;

import de.slothsoft.charts.Chart;
import de.slothsoft.charts.Drawable;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;
import de.slothsoft.charts.Rectangle;

public class LineChart extends Chart {

	private final List<Drawable> drawables = new ArrayList<>();

	@Override
	protected void paintGraph(GraphicContext gc, PaintInstructions instructions) {
		final Rectangle rect = instructions.getArea();

		gc.translate(rect.getX(), rect.getY() + rect.getHeight());
		final PaintInstructions lineInstructions = instructions.copy().area(new Rectangle(rect.getWidth(), rect.getHeight()));

		for (final Drawable drawable : this.drawables) {
			drawable.paintOn(new FlipYGraphicContext(gc), lineInstructions);
		}
		gc.translate(-rect.getX(), -rect.getY() - rect.getHeight());
	}

	public void addLine(Drawable drawable) {
		this.drawables.add(drawable);
	}

}

package de.slothsoft.charts.swt;

import java.util.Objects;
import java.util.function.BiConsumer;

import de.slothsoft.charts.Chart;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;

public class TestChart extends Chart {

	private BiConsumer<GraphicContext, PaintInstructions> paintFunction = (gc, i) -> {
		// noop for default tests
	};

	@Override
	public void fireRefreshNeeded() {
		super.fireRefreshNeeded();
	}

	@Override
	protected void paintGraph(GraphicContext gc, PaintInstructions instructions) {
		this.paintFunction.accept(gc, instructions);
	}

	public BiConsumer<GraphicContext, PaintInstructions> getPaintFunction() {
		return this.paintFunction;
	}

	public TestChart paintFunction(BiConsumer<GraphicContext, PaintInstructions> newPaintFunction) {
		setPaintFunction(newPaintFunction);
		return this;
	}

	public void setPaintFunction(BiConsumer<GraphicContext, PaintInstructions> paintFunction) {
		this.paintFunction = Objects.requireNonNull(paintFunction);
	}

}

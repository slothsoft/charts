package de.slothsoft.charts.internal;

import java.util.Objects;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.GraphicContext;

/**
 * This is a {@link GraphicContext} that only delegates to another one. Used to override
 * only one method.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class DelegatingGraphicContext implements GraphicContext {

	private final GraphicContext delegate;

	public DelegatingGraphicContext(GraphicContext delegate) {
		this.delegate = Objects.requireNonNull(delegate);
	}

	@Override
	public void setColor(int backgroundColor) {
		this.delegate.setColor(backgroundColor);
	}

	@Override
	public int getColor() {
		return this.delegate.getColor();
	}

	@Override
	public void translate(double x, double y) {
		this.delegate.translate(x, y);
	}

	@Override
	public void scale(double factor) {
		this.delegate.scale(factor);
	}

	@Override
	public void scale(double x, double y) {
		this.delegate.scale(x, y);
	}

	@Override
	public void clip(Area rect) {
		this.delegate.clip(rect);
	}

	@Override
	public void drawLine(double x1, double y1, double x2, double y2) {
		this.delegate.drawLine(x1, y1, x2, y2);
	}

	@Override
	public void fillRectangle(double x, double y, double width, double height) {
		this.delegate.fillRectangle(x, y, width, height);
	}

	@Override
	public void drawPolyline(double[] x, double[] y) {
		this.delegate.drawPolyline(x, y);
	}

	@Override
	public void fillPolygon(double[] x, double[] y) {
		this.delegate.fillPolygon(x, y);
	}
}

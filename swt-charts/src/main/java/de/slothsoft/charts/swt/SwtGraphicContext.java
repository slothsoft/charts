package de.slothsoft.charts.swt;

import java.util.Objects;
import java.util.function.Consumer;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.GraphicContext;

/**
 * A {@link GraphicContext} implementation using the SWT {@link GC} to draw on whatever
 * you like.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class SwtGraphicContext implements GraphicContext {

	private GC delegate;

	private int colorAsInt;
	private Color color;

	private Transform transform;
	private double scaleX = 1;
	private double scaleY = 1;

	/**
	 * Constructor.
	 *
	 * @param delegate the GC to draw on; cannot be null
	 */

	public SwtGraphicContext(GC delegate) {
		this.delegate = Objects.requireNonNull(delegate);
	}

	@Override
	public void setColor(int colorAsInt) {
		if (this.color != null) {
			this.color.dispose();
		}

		final int red = (colorAsInt >> 16) & 0xFF;
		final int green = (colorAsInt >> 8) & 0xFF;
		final int blue = colorAsInt & 0xFF;
		this.color = new Color(this.delegate.getDevice(), new RGB(red, green, blue));
		this.delegate.setBackground(this.color);
		this.delegate.setForeground(this.color);
		this.colorAsInt = colorAsInt;
	}

	@Override
	public int getColor() {
		return this.colorAsInt;
	}

	@Override
	public void translate(double x, double y) {
		doToTransform(t -> t.translate((int) x, (int) y));
	}

	private void doToTransform(Consumer<Transform> transformConsumer) {
		final Transform usedTransform = fetchTransform();
		this.delegate.getTransform(usedTransform);
		transformConsumer.accept(usedTransform);
		this.delegate.setTransform(usedTransform);
	}

	Transform fetchTransform() {
		if (this.transform == null) {
			this.transform = new Transform(this.delegate.getDevice());
		}
		return this.transform;
	}

	@Override
	public void scale(double x, double y) {
		this.scaleX *= x;
		this.scaleY *= y;
	}

	@Override
	public void clip(Area rect) {
		if (rect == null) {
			this.delegate.setClipping((Rectangle) null);
		} else {
			this.delegate.setClipping((int) rect.getStartX(), (int) rect.getStartY(),
					(int) (rect.getEndX() - rect.getStartX()), (int) (rect.getEndY() - rect.getStartY()));
		}
	}

	@Override
	public void drawLine(double x1, double y1, double x2, double y2) {
		this.delegate.drawLine((int) (this.scaleX * x1), (int) (this.scaleY * y1), (int) (this.scaleX * x2),
				(int) (this.scaleX * y2));
	}

	@Override
	public void fillRectangle(double x, double y, double width, double height) {
		this.delegate.fillRectangle((int) (this.scaleX * x), (int) (this.scaleY * y), (int) (this.scaleX * width),
				(int) (this.scaleY * height));
	}

	@Override
	public void drawPolyline(double[] x, double[] y) {
		final int[] points = new int[x.length + y.length];
		for (int i = 0; i < x.length; i++) {
			points[2 * i] = (int) (this.scaleX * x[i]);
			points[2 * i + 1] = (int) (this.scaleY * y[i]);
		}
		this.delegate.drawPolyline(points);
	}

	/**
	 * Disposes of the operating system resources associated with this resource.
	 * Applications must dispose of all resources which they allocate. This method does
	 * nothing if the resource is already disposed.
	 */

	public void dispose() {
		if (this.transform != null) {
			this.transform.dispose();
		}
	}

	/**
	 * Returns the GC.
	 *
	 * @return the GC to draw on; never null
	 */

	public GC getDelegate() {
		return this.delegate;
	}

	/**
	 * Sets the GC.
	 *
	 * @param newDelegate the GC to draw on; cannot be null
	 * @return this instance
	 */

	public SwtGraphicContext delegate(GC newDelegate) {
		setDelegate(newDelegate);
		return this;
	}

	/**
	 * Sets the GC.
	 *
	 * @param delegate the GC to draw on; cannot be null
	 */

	public void setDelegate(GC delegate) {
		this.delegate = Objects.requireNonNull(delegate);
	}

}

package de.slothsoft.charts.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import de.slothsoft.charts.Chart;
import de.slothsoft.charts.PaintInstructions;

/**
 * A SWT control displaying a {@link Chart} and hooking it to the SWT framework.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class ChartControl extends Canvas {

	private Chart model;
	private SwtGraphicContext graphicContext;

	/**
	 * Constructs a new instance of this class given its parentand a style value
	 * describing its behavior and appearance.
	 *
	 * @param parent a composite control which will be the parent of the new instance
	 *            (cannot be null)
	 * @param style the style of control to construct
	 */

	public ChartControl(Composite parent, int style) {
		super(parent, style);
		addPaintListener(this::paintControl);
		addDisposeListener(e -> dispose());
	}

	private void paintControl(PaintEvent e) {
		final Rectangle rect = getClientArea();
		e.gc.setAntialias(SWT.ON);

		if (this.model != null) {
			if (this.graphicContext == null) {
				this.graphicContext = new SwtGraphicContext(e.gc);
			} else {
				this.graphicContext.delegate(e.gc);
			}

			final PaintInstructions instructions = new PaintInstructions(
					new de.slothsoft.charts.Area(rect.x, rect.y, rect.width, rect.height));
			this.model.paintOn(this.graphicContext, instructions);
		}
	}

	/**
	 * Returns the {@link Chart} that gets displayed by this control.
	 *
	 * @return the chart
	 */

	public Chart getModel() {
		return this.model;
	}

	/**
	 * Sets the {@link Chart} that gets displayed by this control.
	 *
	 * @param newModel the chart
	 * @return this instance
	 */

	public ChartControl model(Chart newModel) {
		setModel(newModel);
		return this;
	}

	/**
	 * Sets the {@link Chart} that gets displayed by this control.
	 *
	 * @param model the chart
	 */

	public void setModel(Chart model) {
		this.model = model;
		redraw();
	}

	@Override
	public void dispose() {
		if (this.graphicContext != null) {
			this.graphicContext.dispose();
		}
		super.dispose();
	}

}

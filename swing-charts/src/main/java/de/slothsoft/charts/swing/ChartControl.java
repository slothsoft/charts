package de.slothsoft.charts.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.Chart;
import de.slothsoft.charts.PaintInstructions;
import de.slothsoft.charts.RefreshListener;

/**
 * A Swing control displaying a {@link Chart} and hooking it to the Swing framework.
 *
 * @author Stef Schulz
 * @since 0.2.0
 */

public class ChartControl extends JPanel {

	private static final long serialVersionUID = -3869433809324173828L;

	private final RefreshListener refreshListener = e -> repaint();
	private Chart model;

	/**
	 * Default constructor without model.
	 */

	public ChartControl() {
		this(null);
	}

	/**
	 * Default constructor.
	 *
	 * @param model the initial chart
	 */

	public ChartControl(Chart model) {
		setModel(model);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (this.model != null) {
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			final Graphics2DGraphicContext graphicContext = new Graphics2DGraphicContext((Graphics2D) g);
			this.model.paintOn(graphicContext, new PaintInstructions(new Area(getWidth(), getHeight())));
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
		if (this.model != null) {
			this.model.removeRefreshListener(this.refreshListener);
		}
		this.model = model;
		if (this.model != null) {
			this.model.addRefreshListener(this.refreshListener);
		}
		repaint();
	}

}
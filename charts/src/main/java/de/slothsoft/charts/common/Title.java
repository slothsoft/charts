package de.slothsoft.charts.common;

import java.util.Objects;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.Chart;
import de.slothsoft.charts.ChartPart;
import de.slothsoft.charts.Font;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;
import de.slothsoft.charts.RefreshListener;
import de.slothsoft.charts.internal.RefreshListeners;

/**
 * A title over an {@link Chart}. On default it's not visible, you need to use
 * {@link #setText(String)} to get it to be displayed.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class Title implements ChartPart {

	String text;
	int color = 0xFF112211;
	Font font = Font.TITLE;
	int size = 20;

	RefreshListeners refreshListeners = new RefreshListeners(this);

	@Override
	public void paintOn(GraphicContext gc, PaintInstructions instructions) {
		if (this.text != null) {
			final Area area = instructions.getArea();
			gc.setColor(this.color);
			gc.setFont(this.font);
			gc.drawText(area.getStartX(), area.getStartY(), this.text);
		}
	}

	@Override
	public Area snipNecessarySpace(Area existingArea) {
		if (this.text == null) return existingArea;
		return existingArea.copy().startY(existingArea.getStartY() + this.size);
	}

	@Override
	public void addRefreshListener(RefreshListener listener) {
		this.refreshListeners.addRefreshListener(listener);
	}

	@Override
	public void removeRefreshListener(RefreshListener listener) {
		this.refreshListeners.removeRefreshListener(listener);
	}

	/**
	 * Returns the text that is the title or <code>null</code> to show there is no title.
	 *
	 * @return the text
	 */

	public String getText() {
		return this.text;
	}

	/**
	 * Sets the text that is the title or <code>null</code> to show there is no title.
	 *
	 * @param newText the text
	 * @return this instance
	 */

	public Title text(String newText) {
		setText(newText);
		return this;
	}

	/**
	 * Sets the text that is the title or <code>null</code> to show there is no title.
	 *
	 * @param text the text
	 */

	public void setText(String text) {
		final String oldText = this.text;
		this.text = text;
		if (oldText != text) {
			this.refreshListeners.fireRefreshNeeded();
		}
	}

	/**
	 * Returns the color as ARGB int, e.g. red is <code>0xFFFF0000</code> and blue is
	 * <code>0xFF0000FF</code>.
	 *
	 * @return the color
	 */

	public int getColor() {
		return this.color;
	}

	/**
	 * Sets the color as ARGB int, e.g. red is <code>0xFFFF0000</code> and blue is
	 * <code>0xFF0000FF</code>.
	 *
	 * @param newColor the color
	 * @return this instance
	 */

	public Title color(int newColor) {
		setColor(newColor);
		return this;
	}

	/**
	 * Sets the color as ARGB int, e.g. red is <code>0xFFFF0000</code> and blue is
	 * <code>0xFF0000FF</code>.
	 *
	 * @param color the color
	 */

	public void setColor(int color) {
		final int oldColor = this.color;
		this.color = color;
		if (oldColor != color) {
			this.refreshListeners.fireRefreshNeeded();
		}
	}

	/**
	 * Returns the font used to paint the title.
	 *
	 * @return the font; never null
	 */

	public Font getFont() {
		return this.font;
	}

	/**
	 * Sets the font used to paint the title.
	 *
	 * @param newFont the font; cannot be null
	 * @return this instance
	 */

	public Title font(Font newFont) {
		setFont(newFont);
		return this;
	}

	/**
	 * Sets the font used to paint the title.
	 *
	 * @param font the font; cannot be null
	 */

	public void setFont(Font font) {
		final Font oldFont = this.font;
		this.font = Objects.requireNonNull(font);
		if (oldFont != font) {
			this.refreshListeners.fireRefreshNeeded();
		}
	}

	/**
	 * Returns the height of the title, if one is drawn.
	 *
	 * @return the title's size
	 */

	public int getSize() {
		return this.size;
	}

	/**
	 * Sets the height of the title, if one is drawn.
	 *
	 * @param newSize the title's size
	 * @return this instance
	 */

	public Title size(int newSize) {
		setSize(newSize);
		return this;
	}

	/**
	 * Sets the height of the title, if one is drawn.
	 *
	 * @param size the title's size
	 */

	public void setSize(int size) {
		final int oldSize = this.size;
		this.size = size;
		if (oldSize != size) {
			this.refreshListeners.fireRefreshNeeded();
		}
	}

}

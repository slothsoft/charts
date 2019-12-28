package de.slothsoft.charts.common;

import java.util.Objects;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.Chart;
import de.slothsoft.charts.ChartPart;
import de.slothsoft.charts.Font;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;
import de.slothsoft.charts.RefreshListener;
import de.slothsoft.charts.TextAlignment;
import de.slothsoft.charts.internal.RefreshListeners;

/**
 * A title over an {@link Chart}. On default it's not visible, you need to use
 * {@link #setText(String)} to get it to be displayed.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class Title implements ChartPart {

	/**
	 * The position of the {@link Title}.
	 */

	public enum Position {
		/**
		 * The default behavior is to have the axis on y=0 if that is visible, else on the
		 * top or bottom border of the graph depending were it is in relation to the
		 * visible area.
		 */
		TOP {

			@Override
			Area snipNecessarySpace(Area existingArea, double size) {
				return existingArea.copy().startY(existingArea.getStartY() + size);
			}

			@Override
			double getPaintY(Area area, double size) {
				return area.getStartY();
			}
		},

		BOTTOM {

			@Override
			Area snipNecessarySpace(Area existingArea, double size) {
				return existingArea.copy().endY(existingArea.getEndY() - size);
			}

			@Override
			double getPaintY(Area area, double size) {
				return area.getEndY() - size;
			}
		};

		abstract Area snipNecessarySpace(Area existingArea, double size);

		abstract double getPaintY(Area area, double size);

	}
	String text;
	int color = 0xFF112211;
	Font font = Font.TITLE;
	double size = 25;
	Title.Position position = Title.Position.TOP;
	TextAlignment alignment = TextAlignment.CENTER;

	RefreshListeners refreshListeners = new RefreshListeners(this);

	@Override
	public void paintOn(GraphicContext gc, PaintInstructions instructions) {
		if (this.text != null) {
			final Area area = instructions.getArea();
			gc.setColor(this.color);
			gc.setFont(this.font);

			final double paintY = this.position.getPaintY(area, this.size);
			this.alignment.drawText(gc, area.copy().startY(paintY).endY(paintY + this.size), this.text);
		}
	}

	@Override
	public Area snipNecessarySpace(Area existingArea) {
		if (this.text == null) return existingArea;
		return this.position.snipNecessarySpace(existingArea, this.size);
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

	public double getSize() {
		return this.size;
	}

	/**
	 * Sets the height of the title, if one is drawn.
	 *
	 * @param newSize the title's size
	 * @return this instance
	 */

	public Title size(double newSize) {
		setSize(newSize);
		return this;
	}

	/**
	 * Sets the height of the title, if one is drawn.
	 *
	 * @param size the title's size
	 */

	public void setSize(double size) {
		final double oldSize = this.size;
		this.size = size;
		if (oldSize != size) {
			this.refreshListeners.fireRefreshNeeded();
		}
	}

	/**
	 * Returns the position of the title.
	 *
	 * @return the position; never null
	 */

	public Title.Position getPosition() {
		return this.position;
	}

	/**
	 * Sets the position of the title.
	 *
	 * @param newPosition the position; cannot be null
	 * @return this instance
	 */

	public Title position(Title.Position newPosition) {
		setPosition(newPosition);
		return this;
	}

	/**
	 * Sets the position of the title.
	 *
	 * @param position the position; cannot be null
	 */

	public void setPosition(Title.Position position) {
		final Title.Position oldPosition = this.position;
		this.position = Objects.requireNonNull(position);
		if (oldPosition != position) {
			this.refreshListeners.fireRefreshNeeded();
		}
	}

	/**
	 * Returns the alignment of the title.
	 *
	 * @return the alignment; never null
	 */

	public TextAlignment getAlignment() {
		return this.alignment;
	}

	/**
	 * Sets the alignment of the title.
	 *
	 * @param newAlignment the alignment; cannot be null
	 * @return this instance
	 */

	public Title alignment(TextAlignment newAlignment) {
		setAlignment(newAlignment);
		return this;
	}

	/**
	 * Sets the alignment of the title.
	 *
	 * @param alignment the alignment; cannot be null
	 */

	public void setAlignment(TextAlignment alignment) {
		final TextAlignment oldAlignment = this.alignment;
		this.alignment = Objects.requireNonNull(alignment);
		if (oldAlignment != alignment) {
			this.refreshListeners.fireRefreshNeeded();
		}
	}

	@Override
	public String toString() {
		return "Title [text=" + this.text + ", color=" + this.color + ", font=" + this.font + ", size=" + this.size
				+ ", position=" + this.position + ", alignment=" + this.alignment + "]";
	}

}

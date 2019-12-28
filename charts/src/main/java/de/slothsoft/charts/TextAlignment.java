package de.slothsoft.charts;

/**
 * General text alignment that can be used to draw text using an {@link GraphicContext}.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public enum TextAlignment {
	/** Text is displayed on the left. */
	LEFT {
		@Override
		public void drawText(GraphicContext gc, Area area, String text) {
			gc.drawText(area.startX, area.startY, text);
		}
	},

	/** Text is displayed on the right. */
	RIGHT {
		@Override
		public void drawText(GraphicContext gc, Area area, String text) {
			final Area textSize = gc.calculateTextSize(text);
			gc.drawText(area.endX - textSize.endX, area.startY, text);
		}
	},

	/** Text is displayed in the center. */
	CENTER {
		@Override
		public void drawText(GraphicContext gc, Area area, String text) {
			final double areaWidth = area.endX - area.startX;
			final Area textSize = gc.calculateTextSize(text);
			final double textSizeWidth = textSize.endX - textSize.startX;
			gc.drawText(area.startX + (areaWidth - textSizeWidth) / 2, area.startY, text);
		}
	};

	public abstract void drawText(GraphicContext gc, Area area, String text);

}

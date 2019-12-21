package de.slothsoft.charts;

import de.slothsoft.charts.common.Border;

/**
 * This class represents a part of the {@link Chart} that is not used for the actual
 * graph. Classes are connected like this: <br>
 * <img src="https://raw.githubusercontent.com/wiki/slothsoft/charts/images/UML.png" alt=
 * "UML Diagram"><br>
 * Common {@link ChartPart}s are:
 * <ul>
 * <li>{@link Border}</li>
 * </ul>
 *
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public interface ChartPart extends Drawable {

	/**
	 * Paints the current content onto the graphic context. Checks the instructions for
	 * what to paint. Coordinates are starting from the top left with 0|0 and ending
	 * bottom right with something like 800|600.
	 *
	 * @param gc - graphic context; coordinates are relative to the {@link Chart}
	 * @param instructions - additional instructions like the area to paint on
	 */

	@Override
	void paintOn(GraphicContext gc, PaintInstructions instructions);

	/**
	 * This method removes space from the existing area to make room for this part. E.g. a
	 * part on the left of the graph would increase the x and decrease the width.
	 *
	 * @param existingArea - the area this part could use
	 * @return the area for the next parts
	 */

	Rectangle snipNecessarySpace(Rectangle existingArea);
}

package de.slothsoft.charts;

/**
 * This class represents an abstract drawable, weather it's a graph or a rectangle or
 * something else entirely. Classes are connected like this: <br>
 * <img src="https://raw.githubusercontent.com/wiki/slothsoft/charts/images/UML.png" alt=
 * "UML Diagram">
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public interface Drawable {

	/**
	 * Paints the current content onto the graphic context. Checks the instructions for
	 * what to paint.
	 *
	 * @param gc graphic context; coordinates are relative to the {@link Chart}
	 * @param instructions additional instructions like the area to paint on
	 */

	void paintOn(GraphicContext gc, PaintInstructions instructions);

	/**
	 * Adds a refresh listener that is called whenever this {@link Drawable} needs to be
	 * redrawn by the GUI.
	 *
	 * @param listener a listener
	 */

	void addRefreshListener(RefreshListener listener);

	/**
	 * Removes a refresh listener that was called whenever this {@link Drawable} needed to
	 * be redrawn by the GUI. Does nothing if the listener was never added.
	 *
	 * @param listener a listener
	 */

	void removeRefreshListener(RefreshListener listener);
}

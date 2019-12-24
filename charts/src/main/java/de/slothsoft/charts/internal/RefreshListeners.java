package de.slothsoft.charts.internal;

import java.util.ArrayList;
import java.util.List;

import de.slothsoft.charts.RefreshListener;

/**
 * A class that capsules all the functionality needed to get the {@link RefreshListener}
 * to work correctly.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public class RefreshListeners {

	private final Object source;
	final List<RefreshListener> refreshListeners = new ArrayList<>();

	/**
	 * Default constructor.
	 *
	 * @param source the source of the events; can be null
	 */

	public RefreshListeners(Object source) {
		this.source = source;
	}

	/**
	 * Fires a default event for the {@link RefreshListener}s of the source.
	 */

	public void fireRefreshNeeded() {
		fireRefreshNeeded(new RefreshListener.Event(this.source));
	}

	/**
	 * Fires an event for the {@link RefreshListener}s of the source.
	 *
	 * @param event the event to be fired
	 */

	public void fireRefreshNeeded(RefreshListener.Event event) {
		if (this.refreshListeners.isEmpty()) return;
		for (final RefreshListener listener : this.refreshListeners
				.toArray(new RefreshListener[this.refreshListeners.size()])) {
			listener.refreshNeeded(event);
		}
	}

	/**
	 * Adds a refresh listener that is called whenever the source needs to be redrawn by
	 * the GUI.
	 *
	 * @param listener a listener
	 */

	public void addRefreshListener(RefreshListener listener) {
		this.refreshListeners.add(listener);
	}

	/**
	 * Removes a refresh listener that was called whenever the source needed to be redrawn
	 * by the GUI. Does nothing if the listener was never added.
	 *
	 * @param listener a listener
	 */

	public void removeRefreshListener(RefreshListener listener) {
		this.refreshListeners.remove(listener);
	}
}

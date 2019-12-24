package de.slothsoft.charts;

/**
 * A listener that is used whenever an objects needs refreshing and wants to comunicate
 * this fact.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public interface RefreshListener {

	/**
	 * Event for the {@link RefreshListener}.
	 */

	class Event {

		final Object source;

		/**
		 * Default constructor.
		 *
		 * @param source source of the event; can be null
		 */

		public Event(Object source) {
			this.source = source;
		}

		/**
		 * The source of the event.
		 *
		 * @return a source; can be null
		 */

		public Object getSource() {
			return this.source;
		}
	}

	/**
	 * This method indicates something changed on the event's source in a way that makes
	 * refreshing necessary.
	 *
	 * @param event an event with the details; never null
	 */

	void refreshNeeded(RefreshListener.Event event);

}

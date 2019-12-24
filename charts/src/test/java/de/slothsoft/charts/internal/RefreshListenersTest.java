package de.slothsoft.charts.internal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.slothsoft.charts.RefreshListener;

public abstract class RefreshListenersTest {

	RefreshListeners listeners;

	@Before
	public void setUp() {
		this.listeners = new RefreshListeners(this);
	}

	@Test
	public void testAddRefreshListener() throws Exception {
		final RefreshListener.Event[] called = {null};
		this.listeners.addRefreshListener(e -> called[0] = e);

		this.listeners.fireRefreshNeeded();
		Assert.assertNotNull(called[0]);
	}

	@Test
	public void testRemoveRefreshListener() throws Exception {
		final RefreshListener.Event[] called = {null};
		final RefreshListener listener = e -> called[0] = e;
		this.listeners.addRefreshListener(listener);
		this.listeners.removeRefreshListener(listener);

		this.listeners.fireRefreshNeeded();
		Assert.assertNull(called[0]);
	}

	@Test
	public void testRemoveRefreshListenerDuringEvent() throws Exception {
		final RefreshListener listener = new RefreshListener() {

			@Override
			public void refreshNeeded(RefreshListener.Event event) {
				RefreshListenersTest.this.listeners.removeRefreshListener(this);
			}
		};
		this.listeners.addRefreshListener(listener);

		this.listeners.fireRefreshNeeded();
		Assert.assertTrue(this.listeners.refreshListeners.isEmpty());
	}
}

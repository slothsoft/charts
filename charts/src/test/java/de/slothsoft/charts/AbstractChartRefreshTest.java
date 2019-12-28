package de.slothsoft.charts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.slothsoft.charts.common.Title;

/**
 * This class tests the functionality of the {@link RefreshListener}s of {@link Chart} for
 * each implementation of it.
 *
 * @author Stef Schulz
 * @since 0.1.0
 * @param <C> - type of chart
 */

public abstract class AbstractChartRefreshTest<C extends Chart> {

	public static List<Object[]> createData() {
		return new ArrayList<>(Arrays.asList(

				data("setBackgroundColor", chart -> chart.setBackgroundColor(0xFF00FF00)),

				data("backgroundColor", chart -> chart.backgroundColor(0xFF00FF00)),

				data("Border.setSpace", chart -> chart.getBorder().setSpace(1)),

				data("Border.setSpaceOnTop", chart -> chart.getBorder().setSpaceOnTop(1)),

				data("Border.setSpaceOnRight", chart -> chart.getBorder().setSpaceOnRight(1)),

				data("Border.setSpaceOnBottom", chart -> chart.getBorder().setSpaceOnBottom(1)),

				data("Border.setSpaceOnLeft", chart -> chart.getBorder().setSpaceOnLeft(1)),

				data("Border.space", chart -> chart.getBorder().space(1)),

				data("Border.spaceOnTop", chart -> chart.getBorder().spaceOnTop(1)),

				data("Border.spaceOnRight", chart -> chart.getBorder().spaceOnRight(1)),

				data("Border.spaceOnBottom", chart -> chart.getBorder().spaceOnBottom(1)),

				data("Border.spaceOnLeft", chart -> chart.getBorder().spaceOnLeft(1)),

				data("Title.setColor", chart -> chart.getTitle().setColor(0xFF00FF00)),

				data("Title.color", chart -> chart.getTitle().color(0xFF00FF00)),

				data("Title.setFont", chart -> chart.getTitle().setFont(Font.NORMAL)),

				data("Title.font", chart -> chart.getTitle().font(Font.NORMAL)),

				data("Title.setText", chart -> chart.getTitle().setText("Hello")),

				data("Title.text", chart -> chart.getTitle().text("World")),

				data("Title.setSize", chart -> chart.getTitle().setSize(1)),

				data("Title.size", chart -> chart.getTitle().size(2)),

				data("Title.setPosition", chart -> chart.getTitle().setPosition(Title.Position.BOTTOM)),

				data("Title.position", chart -> chart.getTitle().position(Title.Position.BOTTOM)),

				data("Title.setAlignment", chart -> chart.getTitle().setAlignment(TextAlignment.LEFT)),

				data("Title.alignment", chart -> chart.getTitle().alignment(TextAlignment.RIGHT))

		));
	}

	private static Object[] data(String displayName, Consumer<Chart> methodCall) {
		return data(displayName, methodCall, false);
	}

	private static Object[] data(String displayName, Consumer<Chart> methodCall, boolean secondCallChangesToo) {
		return new Object[]{displayName, methodCall, Boolean.valueOf(secondCallChangesToo)};
	}

	private final Consumer<C> methodCall;
	private final boolean secondCallChangesToo;

	protected C chart;

	public AbstractChartRefreshTest(@SuppressWarnings("unused") String displayName, Consumer<C> methodCall,
			boolean secondCallChangesToo) {
		this.methodCall = methodCall;
		this.secondCallChangesToo = secondCallChangesToo;
	}

	@Before
	public void setUp() {
		this.chart = createChart();
	}

	protected abstract C createChart();

	@Test
	public void testRefreshWasExecuted() throws Exception {
		final RefreshListener.Event[] called = {null};
		this.chart.addRefreshListener(e -> called[0] = e);

		this.methodCall.accept(this.chart);
		Assert.assertNotNull(called[0]);
		Assert.assertNotNull(called[0].getSource());
	}

	@Test
	public void testRefreshWasNotExecutedOnMultipleCalls() throws Exception {
		if (this.secondCallChangesToo) {
			this.methodCall.accept(this.chart);

			final RefreshListener.Event[] called = {null};
			this.chart.addRefreshListener(e -> called[0] = e);

			this.methodCall.accept(this.chart);
			Assert.assertNotNull(called[0]);

		} else {
			this.methodCall.accept(this.chart);

			final RefreshListener.Event[] called = {null};
			this.chart.addRefreshListener(e -> called[0] = e);

			this.methodCall.accept(this.chart);
			Assert.assertNull(called[0]);
		}
	}
}

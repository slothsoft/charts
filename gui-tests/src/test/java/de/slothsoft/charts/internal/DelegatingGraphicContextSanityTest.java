package de.slothsoft.charts.internal;

import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.internal.DelegatingGraphicContext;
import de.slothsoft.charts.internal.LogGraphicContext;
import de.slothsoft.charts.test.AbstractGraphicContextSanityTest;

public class DelegatingGraphicContextSanityTest extends AbstractGraphicContextSanityTest {

	@Override
	protected GraphicContext createGraphicContext() {
		return new DelegatingGraphicContext(new LogGraphicContext());
	}

}

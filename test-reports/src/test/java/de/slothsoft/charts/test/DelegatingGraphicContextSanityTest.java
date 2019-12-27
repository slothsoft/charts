package de.slothsoft.charts.test;

import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.internal.DelegatingGraphicContext;
import de.slothsoft.charts.internal.LogGraphicContext;

public class DelegatingGraphicContextSanityTest extends AbstractGraphicContextSanityTest {

	@Override
	protected GraphicContext createGraphicContext() {
		return new DelegatingGraphicContext(new LogGraphicContext());
	}

}

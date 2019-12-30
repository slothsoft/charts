package de.slothsoft.charts.internal;

import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.internal.LogGraphicContext;
import de.slothsoft.charts.test.AbstractGraphicContextSanityTest;

public class LogGraphicContextSanityTest extends AbstractGraphicContextSanityTest {

	@Override
	protected GraphicContext createGraphicContext() {
		return new LogGraphicContext();
	}

}

package de.slothsoft.charts.test;

import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.internal.LogGraphicContext;

public class LogGraphicContextSanityTest extends AbstractGraphicContextSanityTest {

	@Override
	protected GraphicContext createGraphicContext() {
		return new LogGraphicContext();
	}

}

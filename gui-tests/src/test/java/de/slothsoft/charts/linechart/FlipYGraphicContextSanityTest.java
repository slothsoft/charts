package de.slothsoft.charts.linechart;

import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.internal.LogGraphicContext;
import de.slothsoft.charts.test.AbstractGraphicContextSanityTest;

public class FlipYGraphicContextSanityTest extends AbstractGraphicContextSanityTest {

	@Override
	protected GraphicContext createGraphicContext() {
		return new FlipYGraphicContext(new LogGraphicContext());
	}

}

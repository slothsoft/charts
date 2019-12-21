package de.slothsoft.charts;

import org.junit.Test;

import de.slothsoft.charts.PaintInstructions;
import de.slothsoft.charts.Rectangle;

public class PaintInstructionsTest extends BeanTest {

	@Test
	public void testEqual() {
		assertEquals(new PaintInstructions(new Rectangle()), new PaintInstructions(new Rectangle()));
	}

	@Test
	public void testNotEqual() {
		assertNotEquals(new PaintInstructions(new Rectangle()), new PaintInstructions(new Rectangle(1, 2, 3, 4)));
	}

	@Test
	public void testCopy() {
		final PaintInstructions original = new PaintInstructions(new Rectangle(1, 2, 3, 4));
		assertEquals(original, original.copy());
	}

}

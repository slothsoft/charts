package de.slothsoft.charts;

import org.junit.Assert;
import org.junit.Test;

public class PaintInstructionsTest extends BeanTest {

	@Test
	public void testEqual() {
		final PaintInstructions instructions = new PaintInstructions(new Area());
		assertEquals(instructions, instructions);
		assertEquals(new PaintInstructions(new Area()), new PaintInstructions(new Area()));
	}

	@Test
	public void testNotEqual() {
		assertNotEquals(new PaintInstructions(new Area()), new PaintInstructions(new Area(1, 2, 3, 4)));
	}

	@Test
	@SuppressWarnings("static-method")
	public void testEqualSpecialCases() {
		Assert.assertFalse(new PaintInstructions(new Area()).equals(null));
		Assert.assertFalse(new PaintInstructions(new Area()).equals(new Object()));
	}

	@Test
	public void testCopy() {
		final PaintInstructions original = new PaintInstructions(new Area(1, 2, 3, 4));
		assertEquals(original, original.copy());
	}

}

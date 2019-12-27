package de.slothsoft.charts;

import org.junit.Assert;

@SuppressWarnings("static-method")
public class BeanTest {

	protected <T> void assertEquals(T expected, T actual) {
		Assert.assertEquals(expected, actual);
		if (expected != null) {
			Assert.assertEquals(expected.hashCode(), actual.hashCode());
		}
	}

	protected <T> void assertNotEquals(T expected, T actual) {
		Assert.assertNotEquals(expected, actual);
		if (expected != null) {
			Assert.assertNotEquals(expected.hashCode(), actual.hashCode());
		}
	}
}

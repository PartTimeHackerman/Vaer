package vaer.model;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StringCasterTest {
	
	@Test
	public void getValue() throws Exception {
		Integer integer = StringCaster.getValue("123", Integer.class);
		assertTrue(123 == integer);
	}
	
}
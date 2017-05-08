package vaer.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringCasterTest {
	
	@Test
	public void getValue() throws Exception {
		Integer integer = StringCaster.getValue("123", Integer.class);
		assertTrue(123 == integer);
	}
	
}
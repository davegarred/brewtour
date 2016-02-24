package org.garred.brewtour.domain;

import static org.garred.brewtour.domain.Hash.hashFromPassword;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HashTest {

	@Test
	public void testHash() {
		Hash hash = hashFromPassword(new UserId("userId"), "password");
		assertEquals("4c1353e9e73987b685665af53d3529d702c291f7bf2560d141a9aef08fdba590", hash.getValue());
	}
}

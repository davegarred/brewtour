package org.garred.brewtour.domain;

import static org.garred.brewtour.domain.Hash.hashFromPassword;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HashTest {

	@Test
	public void testHash() {
		Hash hash = hashFromPassword(new UserId("userId"), "password");
		assertEquals("a6b0739a02bb5f17683434f03f1964b1dc7b912df3beecc56e3948fd4a9e07dd", hash.getValue());
	}
}

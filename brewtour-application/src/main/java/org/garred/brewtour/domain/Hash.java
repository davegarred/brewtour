package org.garred.brewtour.domain;

import static java.lang.String.format;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Charsets;

public class Hash extends AbstractObject {

	public static final Hash NO_HASH = new Hash("");
	
	private final String value;

	@JsonCreator
	public Hash(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}

	public static Hash hashFromPassword(UserId userId, String password) {
		final String salted = salt(userId, password);
		final MessageDigest md = hash(salted);
		return new Hash(hexString(md));
	}

	private static MessageDigest hash(String salted) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(salted.getBytes(Charsets.UTF_8));
			return md;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private static String hexString(MessageDigest md) {
		return format("%064x", new BigInteger(1, md.digest()));
	}

	private static String salt(UserId userId, String password) {
		return format("brewtour{%s:salt:%s}boss", userId, password);
	}

}

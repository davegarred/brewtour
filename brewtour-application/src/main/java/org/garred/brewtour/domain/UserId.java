package org.garred.brewtour.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public class UserId extends AbstractIdentifier {

	public static final UserId SYSTEM = new UserId("SYSTEM_USER_1");

	@JsonCreator
	public UserId(String id) {
		super(id);
	}

}

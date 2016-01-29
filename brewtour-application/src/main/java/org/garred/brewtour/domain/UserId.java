package org.garred.brewtour.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public class UserId extends AbstractIdentifier {

	@JsonCreator
	public UserId(String id) {
		super(id);
	}

}

package org.garred.brewtour.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public class BeerId extends AbstractIdentifier {

	@JsonCreator
	public BeerId(String id) {
		super(id);
	}

}

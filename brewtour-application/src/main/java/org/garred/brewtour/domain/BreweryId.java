package org.garred.brewtour.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public class BreweryId extends AbstractIdentifier {

	@JsonCreator
	public BreweryId(String id) {
		super(id);
	}

}

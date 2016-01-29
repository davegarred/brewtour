package org.garred.brewtour.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public class LocationId extends AbstractIdentifier {

	@JsonCreator
	public LocationId(String id) {
		super(id);
	}

}

package org.garred.brewtour.application;

import com.fasterxml.jackson.annotation.JsonCreator;

public class LocaleId extends AbstractIdentifier {

	public static final LocaleId SEATTLE = new LocaleId("SEATTLE");
	
	@JsonCreator
	public LocaleId(String id) {
		super(id);
	}

}

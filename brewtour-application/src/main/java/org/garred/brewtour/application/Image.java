package org.garred.brewtour.application;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Image extends AbstractValueObject {

	public static final Image NOT_AVAILABLE = new Image("");

	@JsonCreator
	public Image(String uri) {
		super(uri);
	}

}

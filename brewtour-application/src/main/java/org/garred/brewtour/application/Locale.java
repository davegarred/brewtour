package org.garred.brewtour.application;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Locale extends AbstractEntity<LocaleId> {

	public final LocaleId localeId;
	public final String name;
	public final GoogleMapsParameters googleMapsParameters;
	public final List<LocalePoint> locations;
	
	@JsonCreator
	public Locale(@JsonProperty("locationId") LocaleId localeId, 
			@JsonProperty("name") String name, 
			@JsonProperty("googleMapsParameters") GoogleMapsParameters googleMapsParameters,
			@JsonProperty("locations") List<LocalePoint> locations) {
		super();
		this.localeId = localeId;
		this.name = name;
		this.googleMapsParameters = googleMapsParameters;
		this.locations = locations;
	}

	@Override
	public LocaleId identifier() {
		return this.localeId;
	}

}

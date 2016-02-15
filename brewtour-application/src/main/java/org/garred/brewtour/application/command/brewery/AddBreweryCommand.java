package org.garred.brewtour.application.command.brewery;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddBreweryCommand {

	public final String breweryName;

	@JsonCreator
	public AddBreweryCommand(@JsonProperty("breweryName") String breweryName) {
		this.breweryName = breweryName;
	}

}

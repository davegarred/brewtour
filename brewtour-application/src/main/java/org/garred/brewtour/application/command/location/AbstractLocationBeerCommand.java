package org.garred.brewtour.application.command.location;

import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;

public abstract class AbstractLocationBeerCommand extends AbstractLocationCommand {

	public final String beerName;

	@JsonCreator
	public AbstractLocationBeerCommand(LocationId locationId,String beerName) {
		super(locationId);
		this.beerName = beerName;
	}

}

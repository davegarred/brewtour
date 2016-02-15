package org.garred.brewtour.application.event.beer;

import org.garred.brewtour.domain.BreweryId;

public abstract class AbstractBreweryEvent {

	public final BreweryId breweryId;

	public AbstractBreweryEvent(BreweryId breweryId) {
		this.breweryId = breweryId;
	}

}

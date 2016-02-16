package org.garred.brewtour.application.command.beer;

import org.garred.brewtour.application.command.AggregateCommand;
import org.garred.brewtour.domain.BreweryId;

import com.fasterxml.jackson.annotation.JsonCreator;

public abstract class AbstractBreweryCommand implements AggregateCommand<BreweryId> {

	public final BreweryId breweryId;

	@JsonCreator
	public AbstractBreweryCommand(BreweryId breweryId) {
		this.breweryId = breweryId;
	}

	@Override
	public BreweryId identifier() {
		return this.breweryId;
	}

}

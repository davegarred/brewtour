package org.garred.brewtour.application.command.beer;

import org.garred.brewtour.application.command.AggregateCommand;
import org.garred.brewtour.domain.BeerId;

import com.fasterxml.jackson.annotation.JsonCreator;

public abstract class AbstractBeerCommand implements AggregateCommand<BeerId> {

	public final BeerId beerId;

	@JsonCreator
	public AbstractBeerCommand(BeerId beerId) {
		this.beerId = beerId;
	}


	@Override
	public BeerId identifier() {
		return this.beerId;
	}

}

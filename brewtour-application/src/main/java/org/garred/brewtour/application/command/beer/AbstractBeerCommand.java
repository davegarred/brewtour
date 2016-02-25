package org.garred.brewtour.application.command.beer;

import javax.validation.constraints.NotNull;

import org.garred.brewtour.application.command.AggregateCommand;
import org.garred.brewtour.domain.BeerId;

import com.fasterxml.jackson.annotation.JsonCreator;

public abstract class AbstractBeerCommand implements AggregateCommand<BeerId> {

	@NotNull
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

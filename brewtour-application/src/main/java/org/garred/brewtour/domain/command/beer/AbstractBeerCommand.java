package org.garred.brewtour.domain.command.beer;

import javax.validation.constraints.NotNull;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.command.AggregateCommand;

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

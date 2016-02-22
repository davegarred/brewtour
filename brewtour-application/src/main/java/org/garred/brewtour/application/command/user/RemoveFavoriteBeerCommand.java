package org.garred.brewtour.application.command.user;

import org.garred.brewtour.domain.BeerId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RemoveFavoriteBeerCommand extends AbstractUserFiredCommand {

	public final BeerId beerId;

	@JsonCreator
	public RemoveFavoriteBeerCommand(@JsonProperty("beerId") BeerId beerId) {
		this.beerId = beerId;
	}

}

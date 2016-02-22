package org.garred.brewtour.application.command.user;

import org.garred.brewtour.domain.BeerId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddFavoriteBeerCommand extends AbstractUserFiredCommand {

	public final BeerId beerId;

	@JsonCreator
	public AddFavoriteBeerCommand(@JsonProperty("beerId") BeerId beerId) {
		this.beerId = beerId;
	}

}

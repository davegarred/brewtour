package org.garred.brewtour.domain.command.location;

import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddLocationCommentCommand extends AbstractLocationCommand {

	public final String comment;

	@JsonCreator
	public AddLocationCommentCommand(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("comment") String comment) {
		super(locationId);
		this.comment = comment;
	}

}

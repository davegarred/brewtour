package org.garred.brewtour.application.command.location;

import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateLocationWebsiteCommand extends AbstractLocationCommand {

    public final String website;

	@JsonCreator
	public UpdateLocationWebsiteCommand(
			@JsonProperty("locationId") LocationId locationId,
	        @JsonProperty("website") String website
	        ) {
		super(locationId);
        this.website = website;
	}

}

package org.garred.brewtour.application.command.location;

import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateLocationImagesCommand extends AbstractLocationCommand {

    public final AvailableImages images;

	@JsonCreator
	public UpdateLocationImagesCommand(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("images") AvailableImages images) {
		super(locationId);
        this.images = images;
	}

}

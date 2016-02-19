package org.garred.brewtour.application.command.beer;

import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.BeerId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateBeerImagesCommand extends AbstractBeerCommand {

    public final AvailableImages images;

	@JsonCreator
	public UpdateBeerImagesCommand(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("images") AvailableImages images) {
		super(beerId);
		this.images = images;
	}

}

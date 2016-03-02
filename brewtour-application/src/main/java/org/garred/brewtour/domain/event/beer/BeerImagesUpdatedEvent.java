package org.garred.brewtour.domain.event.beer;

import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.command.beer.UpdateBeerImagesCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerImagesUpdatedEvent extends AbstractBeerEvent {

    public final AvailableImages images;

	@JsonCreator
	public BeerImagesUpdatedEvent(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("images") AvailableImages images) {
		super(beerId);
		this.images = images;
	}

	public static BeerImagesUpdatedEvent fromCommand(UpdateBeerImagesCommand command) {
		return new BeerImagesUpdatedEvent(command.beerId, command.images);
	}
}

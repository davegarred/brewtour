package org.garred.brewtour.application.command.beer;

import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;

import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.security.SecuredCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SecuredCommand(ADMIN_ROLE)
public class UpdateBeerImagesCommand extends AbstractBeerCommand {

    public final AvailableImages images;

	@JsonCreator
	public UpdateBeerImagesCommand(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("images") AvailableImages images) {
		super(beerId);
		this.images = images;
	}

}

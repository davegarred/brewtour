package org.garred.brewtour.domain.command.beer;

import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.security.SecuredCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SecuredCommand(ADMIN_ROLE)
public class UpdateBeerDescriptionCommand extends AbstractBeerCommand {

    public final String description;

	@JsonCreator
	public UpdateBeerDescriptionCommand(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("description") String description) {
		super(beerId);
		this.description = description;
	}

}

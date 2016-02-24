package org.garred.brewtour.application.command.beer;

import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.security.SecuredCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SecuredCommand(ADMIN_ROLE)
public class UpdateBeerStyleCommand extends AbstractBeerCommand {

    public final String style;

	@JsonCreator
	public UpdateBeerStyleCommand(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("style") String style) {
		super(beerId);
		this.style = style;
	}

}

package org.garred.brewtour.application.command.beer;

import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;

import java.math.BigDecimal;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.security.SecuredCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SecuredCommand(ADMIN_ROLE)
public class UpdateBeerSrmCommand extends AbstractBeerCommand {

    public final BigDecimal srm;

	@JsonCreator
	public UpdateBeerSrmCommand(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("srm") BigDecimal srm) {
		super(beerId);
		this.srm = srm;
	}

}

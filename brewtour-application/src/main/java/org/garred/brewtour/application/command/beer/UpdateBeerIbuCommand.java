package org.garred.brewtour.application.command.beer;

import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;

import java.math.BigDecimal;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.security.SecuredCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SecuredCommand(ADMIN_ROLE)
public class UpdateBeerIbuCommand extends AbstractBeerCommand {

    public final BigDecimal ibu;

	@JsonCreator
	public UpdateBeerIbuCommand(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("ibu") BigDecimal ibu) {
		super(beerId);
		this.ibu = ibu;
	}

}

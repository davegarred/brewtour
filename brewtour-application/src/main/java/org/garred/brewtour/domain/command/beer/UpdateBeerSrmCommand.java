package org.garred.brewtour.domain.command.beer;

import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.security.SecuredCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SecuredCommand(ADMIN_ROLE)
public class UpdateBeerSrmCommand extends AbstractBeerCommand {

	@Min(value = 0)
	@Digits(fraction = 0, integer = 4)
    public final BigDecimal srm;

	@JsonCreator
	public UpdateBeerSrmCommand(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("srm") BigDecimal srm) {
		super(beerId);
		this.srm = srm;
	}

}

package org.garred.brewtour.domain.command.beer;

import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.security.SecuredCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SecuredCommand(ADMIN_ROLE)
public class UpdateBeerAbvCommand extends AbstractBeerCommand {

	@DecimalMin(value = "0.0")
	@Digits(fraction = 1, integer = 2)
    public final BigDecimal abv;

	@JsonCreator
	public UpdateBeerAbvCommand(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("abv") BigDecimal abv) {
		super(beerId);
		this.abv = abv;
	}

}

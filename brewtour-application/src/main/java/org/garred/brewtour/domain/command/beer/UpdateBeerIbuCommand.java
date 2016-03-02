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
public class UpdateBeerIbuCommand extends AbstractBeerCommand {

	@Min(value = 0)
	@Digits(fraction = 0, integer = 3)
    public final BigDecimal ibu;

	@JsonCreator
	public UpdateBeerIbuCommand(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("ibu") BigDecimal ibu) {
		super(beerId);
		this.ibu = ibu;
	}

}

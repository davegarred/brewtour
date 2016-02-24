package org.garred.brewtour.application.command.beer;

import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;

import java.math.BigDecimal;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.security.SecuredCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SecuredCommand(ADMIN_ROLE)
public class ModifyBeerCommand extends AbstractBeerCommand {

	public final String description,style,category;
	public final BigDecimal abv,ibu;

	@JsonCreator
	public ModifyBeerCommand(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("description") String description,
			@JsonProperty("style") String style,
			@JsonProperty("category") String category,
			@JsonProperty("abv") BigDecimal abv,
			@JsonProperty("ibu") BigDecimal ibu) {
		super(beerId);
		this.description = description;
		this.style = style;
		this.category = category;
		this.abv = abv;
		this.ibu = ibu;
	}

}

package org.garred.brewtour.application.command.beer;

import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;

import java.math.BigDecimal;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.ProfessionalRatingGroup;
import org.garred.brewtour.security.SecuredCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SecuredCommand(ADMIN_ROLE)
public class UpdateBeerProfessionalRatingCommand extends AbstractBeerCommand {

	public final ProfessionalRatingGroup ratingGroup;
	public final String link;
    public final BigDecimal rating;
    public final BigDecimal ratingMax;

	@JsonCreator
	public UpdateBeerProfessionalRatingCommand(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("ratingGroup") ProfessionalRatingGroup ratingGroup,
			@JsonProperty("link") String link,
			@JsonProperty("rating") BigDecimal rating,
			@JsonProperty("ratingMax") BigDecimal ratingMax
			) {
		super(beerId);
		this.ratingGroup = ratingGroup;
		this.link = link;
		this.rating = rating;
		this.ratingMax = ratingMax;
	}

}

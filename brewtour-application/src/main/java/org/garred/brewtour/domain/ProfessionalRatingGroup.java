package org.garred.brewtour.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ProfessionalRatingGroup extends AbstractValueObject {

	public static final ProfessionalRatingGroup RATE_BEER = new ProfessionalRatingGroup("RATE_BEER");
	public static final ProfessionalRatingGroup BEER_ADVOCATE = new ProfessionalRatingGroup("BEER_ADVOCATE");


	@JsonCreator
	public ProfessionalRatingGroup(String groupName) {
		super(groupName);
	}

}

package org.garred.brewtour.application;

import javax.sql.DataSource;

import org.garred.brewtour.domain.BeerId;

public class H2BeerIdentifierFactory extends AbstractH2IdentifierFactory<BeerId> {

	private static final String LOCATION_SEQUENCE = "beerSequence";
	private static final String IDENTIFIER_PREFIX = "B";

	public H2BeerIdentifierFactory(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	protected String sequenceName() {
		return LOCATION_SEQUENCE;
	}

	@Override
	protected BeerId newIdentifier(long sequence) {
		return new BeerId(IDENTIFIER_PREFIX + sequence);
	}

}

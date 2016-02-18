package org.garred.brewtour.application;

import javax.sql.DataSource;

import org.garred.brewtour.domain.BreweryId;

public class H2BreweryIdentifierFactory extends AbstractH2IdentifierFactory<BreweryId> {

	private static final String LOCATION_SEQUENCE = "brewerySequence";
	private static final String IDENTIFIER_PREFIX = "BREW";

	public H2BreweryIdentifierFactory(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	protected String sequenceName() {
		return LOCATION_SEQUENCE;
	}

	@Override
	protected BreweryId newIdentifier(long sequence) {
		return new BreweryId(IDENTIFIER_PREFIX + sequence);
	}

}

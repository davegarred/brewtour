package org.garred.brewtour.application;

import javax.sql.DataSource;

import org.garred.brewtour.domain.LocationId;

public class H2LocationIdentifierFactory extends AbstractH2IdentifierFactory<LocationId> {

	private static final String LOCATION_SEQUENCE = "locationSequence";
	private static final String IDENTIFIER_PREFIX = "LOCA";

	public H2LocationIdentifierFactory(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	protected String sequenceName() {
		return LOCATION_SEQUENCE;
	}

	@Override
	protected LocationId newIdentifier(long sequence) {
		return new LocationId(IDENTIFIER_PREFIX + sequence);
	}

}

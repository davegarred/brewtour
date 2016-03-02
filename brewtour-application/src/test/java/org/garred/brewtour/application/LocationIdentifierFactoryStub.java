package org.garred.brewtour.application;

import java.util.concurrent.atomic.AtomicInteger;

import org.garred.brewtour.domain.IdentifierFactory;
import org.garred.brewtour.domain.LocationId;

public class LocationIdentifierFactoryStub implements IdentifierFactory<LocationId> {

	private final AtomicInteger count = new AtomicInteger(10000);

	@Override
	public LocationId next() {
		return new LocationId("LOCA" + this.count.incrementAndGet());
	}

	@Override
	public LocationId last() {
		return new LocationId("LOCA" + this.count.get());
	}

}

package org.garred.brewtour.application.cqrs;

import java.util.concurrent.atomic.AtomicInteger;

import org.garred.brewtour.application.LocationId;

public class LocationIdentifierFactoryStub implements IdentifierFactory<LocationId> {

	private final AtomicInteger count = new AtomicInteger(10000);

	@Override
	public LocationId next() {
		return new LocationId("LOCA" + this.count.incrementAndGet());
	}

}

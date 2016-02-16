package org.garred.brewtour.application;

import java.util.concurrent.atomic.AtomicInteger;

import org.garred.brewtour.domain.BreweryId;

public class BreweryIdentifierFactoryStub implements IdentifierFactory<BreweryId> {

	private final AtomicInteger count = new AtomicInteger(10100);

	@Override
	public BreweryId next() {
		return new BreweryId("BREW" + this.count.incrementAndGet());
	}

	@Override
	public BreweryId last() {
		return new BreweryId("BREW" + this.count.get());
	}

}

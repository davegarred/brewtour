package org.garred.brewtour.application;

import java.util.concurrent.atomic.AtomicInteger;

import org.garred.brewtour.domain.BeerId;

public class BeerIdentifierFactoryStub implements IdentifierFactory<BeerId> {

	private final AtomicInteger count = new AtomicInteger(10100);

	@Override
	public BeerId next() {
		return new BeerId("BEER" + this.count.incrementAndGet());
	}

	@Override
	public BeerId last() {
		return new BeerId("BEER" + this.count.get());
	}

}

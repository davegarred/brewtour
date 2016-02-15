package org.garred.brewtour.application;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.garred.brewtour.domain.BreweryId;

@SuppressWarnings("serial")
public class Brewery extends AbstractAnnotatedAggregateRoot<BreweryId> {

    @AggregateIdentifier
    private BreweryId id;
	private String name;

	public Brewery() {}

	public static Brewery addBrewery(BreweryId id, String name) {
		final Brewery brewery = new Brewery();
		brewery.id = id;
		brewery.name = name;
		return brewery;
	}

}

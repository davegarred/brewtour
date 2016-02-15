package org.garred.brewtour.application.event.beer;

import java.math.BigDecimal;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.BreweryId;

public class AbstractBeerAddedEvent extends AbstractBeerEvent {

	public final String beerName;
	public final BreweryId breweryId;
	public final String breweryName;
	public final String style;
	public final String category;
	public final BigDecimal abv,ibu;

	public AbstractBeerAddedEvent(
			BeerId beerId,
			String beerName,
			BreweryId breweryId,
			String breweryName,
			String style,
			String category,
			BigDecimal abv,
			BigDecimal ibu) {
		super(beerId);
		this.beerName = beerName;
		this.breweryId = breweryId;
		this.breweryName = breweryName;
		this.style = style;
		this.category = category;
		this.abv = abv;
		this.ibu = ibu;
	}



}

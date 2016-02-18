package org.garred.brewtour.application.command.beer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.garred.brewtour.application.command.AddAggregateCallback;
import org.garred.brewtour.application.command.AddAggregateCommand;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.BreweryId;

public abstract class AbstractAddBeerCommand implements AddAggregateCommand<BeerId> {

	private final List<AddAggregateCallback<BeerId>> callbacks = new ArrayList<>();

	public final String beerName;
	public final BreweryId breweryId;
	public final String breweryName;
	public final String style;
	public final String category;
	public final BigDecimal abv,ibu;

	public AbstractAddBeerCommand(
			String beerName,
			BreweryId breweryId,
			String breweryName,
			String style,
			String category,
			BigDecimal abv,
			BigDecimal ibu) {
		this.beerName = beerName;
		this.breweryId = breweryId;
		this.breweryName = breweryName;
		this.style = style;
		this.category = category;
		this.abv = abv;
		this.ibu = ibu;
	}

	@Override
	public void subscribe(AddAggregateCallback<BeerId> callback) {
		if(callback != null) {
			this.callbacks.add(callback);
		}
	}

	@Override
	public void identified(BeerId identifier) {
		this.callbacks.stream().forEach(c -> c.callback(identifier));
	}

}

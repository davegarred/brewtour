package org.garred.brewtour.application.command.beer;

import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.garred.brewtour.application.command.AddAggregateCallback;
import org.garred.brewtour.application.command.AddAggregateCommand;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.BreweryId;
import org.garred.brewtour.security.SecuredCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SecuredCommand(ADMIN_ROLE)
public class AddBeerCommand  implements AddAggregateCommand<BeerId> {

	private final List<AddAggregateCallback<BeerId>> callbacks = new ArrayList<>();

	public final String beerName;
	public final String description;
	public final BreweryId breweryId;
	public final String breweryName;
	public final String style;
	public final String category;
	public final BigDecimal abv,ibu;
	
	@JsonCreator
	public AddBeerCommand(
			@JsonProperty("beerName") String beerName,
			@JsonProperty("description") String description,
			@JsonProperty("breweryId") BreweryId breweryId,
			@JsonProperty("breweryName") String breweryName,
			@JsonProperty("style") String style,
			@JsonProperty("category") String category,
			@JsonProperty("abv") BigDecimal abv,
			@JsonProperty("ibu") BigDecimal ibu) {
		this.beerName = beerName;
		this.description = description;
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

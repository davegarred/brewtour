package org.garred.brewtour.domain.command.beer;

import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.command.AddAggregateCallback;
import org.garred.brewtour.domain.command.AddAggregateCommand;
import org.garred.brewtour.security.SecuredCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SecuredCommand(ADMIN_ROLE)
public class AddBeerCommand  implements AddAggregateCommand<BeerId> {

	private final List<AddAggregateCallback<BeerId>> callbacks = new ArrayList<>();

	@NotNull
	public final String beerName;
	@NotNull
	public final LocationId breweryId;
	@NotNull
	public final String breweryName;

	@JsonCreator
	public AddBeerCommand(
			@JsonProperty("beerName") String beerName,
			@JsonProperty("breweryId") LocationId breweryId,
			@JsonProperty("breweryName") String breweryName) {
		this.beerName = beerName;
		this.breweryId = breweryId;
		this.breweryName = breweryName;
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

package org.garred.brewtour.application.command.brewery;

import java.util.ArrayList;
import java.util.List;

import org.garred.brewtour.application.command.AddAggregateCallback;
import org.garred.brewtour.application.command.AddAggregateCommand;
import org.garred.brewtour.domain.BreweryId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddBreweryCommand  implements AddAggregateCommand<BreweryId> {

	private final List<AddAggregateCallback<BreweryId>> callbacks = new ArrayList<>();

	public final String breweryName;

	@JsonCreator
	public AddBreweryCommand(@JsonProperty("breweryName") String breweryName) {
		this.breweryName = breweryName;
	}

	@Override
	public void subscribe(AddAggregateCallback<BreweryId> callback) {
		if(callback != null) {
			this.callbacks.add(callback);
		}
	}

	@Override
	public void identified(BreweryId identifier) {
		this.callbacks.stream().forEach(c -> c.callback(identifier));
	}
}

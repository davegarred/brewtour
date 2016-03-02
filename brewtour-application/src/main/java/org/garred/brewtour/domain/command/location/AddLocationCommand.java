package org.garred.brewtour.domain.command.location;

import java.util.ArrayList;
import java.util.List;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.command.AddAggregateCallback;
import org.garred.brewtour.domain.command.AddAggregateCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddLocationCommand implements AddAggregateCommand<LocationId> {

	private final List<AddAggregateCallback<LocationId>> callbacks = new ArrayList<>();

    public final String name;

	@JsonCreator
	public AddLocationCommand(@JsonProperty("name") String name) {
        this.name = name;
	}

	@Override
	public void subscribe(AddAggregateCallback<LocationId> callback) {
		if(callback != null) {
			this.callbacks.add(callback);
		}
	}

	@Override
	public void identified(LocationId identifier) {
		this.callbacks.stream().forEach(c -> c.callback(identifier));
	}
}

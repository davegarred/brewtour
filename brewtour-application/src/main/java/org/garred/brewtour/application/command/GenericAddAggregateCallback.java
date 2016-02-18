package org.garred.brewtour.application.command;

import org.garred.brewtour.domain.AbstractIdentifier;

public class GenericAddAggregateCallback<I extends AbstractIdentifier> implements AddAggregateCallback<I> {

	private I id;

	public static <I extends AbstractIdentifier> GenericAddAggregateCallback<I> forCommand(AddAggregateCommand<I> command) {
		final GenericAddAggregateCallback<I> callback = new GenericAddAggregateCallback<>();
		command.subscribe(callback);
		return callback;
	}


	@Override
	public void callback(I identifier) {
		this.id = identifier;
	}

	public I identifier() {
		return this.id;
	}

}

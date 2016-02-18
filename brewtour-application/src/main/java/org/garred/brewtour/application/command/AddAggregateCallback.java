package org.garred.brewtour.application.command;

import org.garred.brewtour.domain.AbstractIdentifier;

public interface AddAggregateCallback<I extends AbstractIdentifier> {

	void callback(I identifier);

}

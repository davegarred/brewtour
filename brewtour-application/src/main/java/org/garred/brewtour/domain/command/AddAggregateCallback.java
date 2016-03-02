package org.garred.brewtour.domain.command;

import org.garred.brewtour.domain.AbstractIdentifier;

public interface AddAggregateCallback<I extends AbstractIdentifier> {

	void callback(I identifier);

}

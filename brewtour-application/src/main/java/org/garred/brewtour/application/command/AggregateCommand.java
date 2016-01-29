package org.garred.brewtour.application.command;

import org.garred.brewtour.domain.AbstractIdentifier;

public interface AggregateCommand<I extends AbstractIdentifier> {

	I identifier();

}

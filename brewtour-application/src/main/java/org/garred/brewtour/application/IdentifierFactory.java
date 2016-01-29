package org.garred.brewtour.application;

import org.garred.brewtour.domain.AbstractIdentifier;

public interface IdentifierFactory<T extends AbstractIdentifier> {

	T next();

}

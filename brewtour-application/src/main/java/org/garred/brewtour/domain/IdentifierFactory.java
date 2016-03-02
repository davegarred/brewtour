package org.garred.brewtour.domain;

public interface IdentifierFactory<T extends AbstractIdentifier> {

	T next();
	T last();

}

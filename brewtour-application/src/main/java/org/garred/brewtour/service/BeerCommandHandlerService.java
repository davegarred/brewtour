package org.garred.brewtour.service;

import java.time.LocalDateTime;

import org.garred.brewtour.domain.BeerId;

public interface BeerCommandHandlerService {

	BeerId nextBeerId();
	LocalDateTime now();

}

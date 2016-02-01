package org.garred.brewtour.service;

import java.time.LocalDateTime;

import org.garred.brewtour.domain.LocationId;

public interface LocationCommandHandlerService {

	LocationId nextLocationId();
	LocalDateTime now();

}

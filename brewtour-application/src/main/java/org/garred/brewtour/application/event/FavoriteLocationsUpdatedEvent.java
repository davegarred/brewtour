package org.garred.brewtour.application.event;

import java.util.Set;

import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.application.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FavoriteLocationsUpdatedEvent extends AbstractUserEvent {

	public final Set<LocationId> favoriteLocations;

	@JsonCreator
	public FavoriteLocationsUpdatedEvent(@JsonProperty("userId") UserId userId,
			@JsonProperty("favoriteLocations") Set<LocationId> favoriteLocations) {
		super(userId);
		this.favoriteLocations = favoriteLocations;
	}

}

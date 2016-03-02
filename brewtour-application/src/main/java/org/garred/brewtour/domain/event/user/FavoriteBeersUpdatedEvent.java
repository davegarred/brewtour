package org.garred.brewtour.domain.event.user;

import java.util.Set;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FavoriteBeersUpdatedEvent extends AbstractUserEvent {

	public final Set<BeerId> favoriteBeers;

	@JsonCreator
	public FavoriteBeersUpdatedEvent(@JsonProperty("userId") UserId userId,
			@JsonProperty("favoriteBeers") Set<BeerId> favoriteBeers) {
		super(userId);
		this.favoriteBeers = favoriteBeers;
	}

}

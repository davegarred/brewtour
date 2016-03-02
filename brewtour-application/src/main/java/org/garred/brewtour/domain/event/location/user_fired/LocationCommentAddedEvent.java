package org.garred.brewtour.domain.event.location.user_fired;

import java.time.LocalDateTime;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.domain.command.location.AddLocationCommentCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationCommentAddedEvent extends AbstractUserFiredLocationEvent {

	public final LocalDateTime time;
	public final String comment;

	@JsonCreator
	public LocationCommentAddedEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("userId") UserId userId,
			@JsonProperty("userScreenName") String userScreenName,
			@JsonProperty("time") LocalDateTime time,
			@JsonProperty("comment") String comment) {
		super(locationId, userId, userScreenName);
		this.time = time;
		this.comment = comment;
	}

	public static LocationCommentAddedEvent fromCommand(AddLocationCommentCommand command, UserId userId, String userScreenName, LocalDateTime time) {
		return new LocationCommentAddedEvent(command.locationId, userId, userScreenName, time, command.comment);
	}

}

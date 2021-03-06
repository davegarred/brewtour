package org.garred.brewtour.domain.command.user;

import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.domain.command.AggregateCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class AbstractUserCommand implements AggregateCommand<UserId> {

	public final UserId userId;

	@JsonCreator
	public AbstractUserCommand(@JsonProperty("userId") UserId userId) {
		this.userId = userId;
	}

	@Override
	public UserId identifier() {
		return this.userId;
	}

}

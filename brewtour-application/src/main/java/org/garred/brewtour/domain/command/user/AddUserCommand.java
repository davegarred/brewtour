package org.garred.brewtour.domain.command.user;

import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;

import java.util.ArrayList;
import java.util.List;

import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.domain.command.AddAggregateCallback;
import org.garred.brewtour.domain.command.AddAggregateCommand;
import org.garred.brewtour.security.SecuredCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SecuredCommand(ADMIN_ROLE)
public class AddUserCommand implements AddAggregateCommand<UserId> {

	private final List<AddAggregateCallback<UserId>> callbacks = new ArrayList<>();

	public final String screenName;
	public final String login;
	public final String password;

	@JsonCreator
	public AddUserCommand(
			@JsonProperty("screenName") String screenName,
			@JsonProperty("login") String login,
			@JsonProperty("password") String password) {
		this.screenName = screenName;
        this.login = login;
        this.password = password;
	}

	@Override
	public void subscribe(AddAggregateCallback<UserId> callback) {
		if(callback != null) {
			this.callbacks.add(callback);
		}
	}

	@Override
	public void identified(UserId identifier) {
		this.callbacks.stream().forEach(c -> c.callback(identifier));
	}
}

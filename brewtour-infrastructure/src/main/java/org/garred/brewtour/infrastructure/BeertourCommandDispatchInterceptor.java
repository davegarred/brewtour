package org.garred.brewtour.infrastructure;

import java.util.HashMap;
import java.util.Map;

import org.axonframework.commandhandling.CommandDispatchInterceptor;
import org.axonframework.commandhandling.CommandMessage;
import org.garred.brewtour.security.SecuredCommand;
import org.garred.brewtour.security.UserAuth;
import org.garred.brewtour.security.UserAuthorizationException;
import org.garred.brewtour.security.UserHolder;
import org.garred.brewtour.security.UserNotLoggedInException;
import org.joda.time.LocalDateTime;

public class BeertourCommandDispatchInterceptor implements CommandDispatchInterceptor {

	@Override
	public CommandMessage<?> handle(CommandMessage<?> commandMessage) {
		final UserAuth user = UserHolder.get();
		if(user == null) {
			throw new UserNotLoggedInException();
		}
		final Map<String,Object> metadata = new HashMap<>();
		metadata.put("userId", user.identifier());
		metadata.put("time", LocalDateTime.now().toString());
		validateSecured(commandMessage, user);
		return commandMessage.andMetaData(metadata);
	}

	private static void validateSecured(CommandMessage<?> commandMessage, UserAuth user) {
		final Object command = commandMessage.getPayload();
		final SecuredCommand annotation = command.getClass().getAnnotation(SecuredCommand.class);
		if(annotation == null) {
			return;
		}
		final String requiredRole = annotation.value();
		if(!user.roles().contains(requiredRole)) {
			throw new UserAuthorizationException();
		}
	}

}

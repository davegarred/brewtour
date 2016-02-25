package org.garred.brewtour.infrastructure;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.axonframework.commandhandling.CommandDispatchInterceptor;
import org.axonframework.commandhandling.CommandMessage;
import org.garred.brewtour.validation.ConstraintViolationException;

public class ValidationCommandDispatchInterceptor implements CommandDispatchInterceptor {

	private final Validator validator;

	public ValidationCommandDispatchInterceptor(Validator validator) {
		this.validator = validator;
	}

	@Override
	public CommandMessage<?> handle(CommandMessage<?> commandMessage) {
		final Set<ConstraintViolation<Object>> result = this.validator.validate((Object)commandMessage.getPayload());
		if(!result.isEmpty()) {
			throw new ConstraintViolationException(result);
		}
		return commandMessage;
	}

}

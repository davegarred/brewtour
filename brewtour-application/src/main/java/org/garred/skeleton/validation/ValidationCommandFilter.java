package org.garred.skeleton.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.nullgeodesic.cqrs.CommandFilter;
import com.nullgeodesic.cqrs.api.Command;

public class ValidationCommandFilter implements CommandFilter {

	private final Validator validator;
	
	public ValidationCommandFilter(Validator validator) {
		this.validator = validator;
	}
	
	@Override
	public Command<?> handle(Command<?> command) {
		Set<ConstraintViolation<Object>> validationResult = validator.validate(command);
		if(!validationResult.isEmpty()) {
			throw new ConstraintViolationException(validationResult);
		}
		return command;
	}

}

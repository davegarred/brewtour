package org.garred.brewtour.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.garred.brewtour.security.LoginNotFoundException;
import org.garred.brewtour.security.UserNotLoggedInException;
import org.garred.brewtour.validation.ConstraintViolationDto;
import org.garred.brewtour.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class AbstractRestController {

	private final ObjectMapper objectMapper;

	public AbstractRestController() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.registerModule(new JavaTimeModule());
	}

	@ExceptionHandler(value = LoginNotFoundException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String loginNotFoundException() throws JsonProcessingException {
		return this.objectMapper.writeValueAsString(new ErrorBody("Username not found or password is incorrect"));
	}

	@ExceptionHandler(value = UserNotLoggedInException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String userNotLoggedInException() throws JsonProcessingException {
		return this.objectMapper.writeValueAsString(new ErrorBody("This resource is only available to registered users"));
	}

	@ExceptionHandler(value = ConstraintViolationException.class)
	@ResponseBody
	@ResponseStatus(value = BAD_REQUEST)
	public String constraintViolation(ConstraintViolationException e) throws JsonProcessingException {
		return this.objectMapper.writeValueAsString(new ConstraintViolationDto(e.getViolations()));
	}

	public static class ErrorBody {
		public final String error;

		public ErrorBody(String error) {
			this.error = error;
		}

	}

}

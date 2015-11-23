package org.garred.brewtour.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import javax.servlet.http.HttpServletRequest;

import org.garred.brewtour.application.UserId;
import org.garred.brewtour.filter.AuthenticationFilter;
import org.garred.brewtour.validation.ConstraintViolationDto;
import org.garred.brewtour.validation.ConstraintViolationException;
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

	@ExceptionHandler(value = ConstraintViolationException.class)
	@ResponseBody
	@ResponseStatus(value = BAD_REQUEST)
	public String constraintViolation(ConstraintViolationException e) throws JsonProcessingException {
		return this.objectMapper.writeValueAsString(new ConstraintViolationDto(e.getViolations()));
	}

	protected UserId userId(HttpServletRequest request) {
		return (UserId) request.getSession().getAttribute(AuthenticationFilter.USER_ATTR);
	}
}

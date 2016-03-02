package org.garred.brewtour.config;

import org.garred.brewtour.domain.command.location.AbstractLocationCommand;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomLocationCommand {
	public String locationName;
	public String commandClass;
	public String serializedCommand;
	
	public static CustomLocationCommand genericCustomLocationCommand(String locationBreweryName, AbstractLocationCommand command, ObjectMapper objectMapper) {
		CustomLocationCommand cust = new CustomLocationCommand();
		cust.locationName = locationBreweryName;
		cust.commandClass = command.getClass().getCanonicalName();
		try {
			cust.serializedCommand = objectMapper.writeValueAsString(command);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return cust;
	}
}
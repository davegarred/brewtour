package org.garred.brewtour.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.io.Reader;

import org.garred.brewtour.service.LocationQueryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
@RequestMapping(value = "beer")
public class BeerController extends AbstractRestController {

	private final LocationQueryService locationService;

	public BeerController(LocationQueryService locationService) {
		this.locationService = locationService;
	}

	@RequestMapping(value = "{commandName}", method = POST, produces="application/json")
	@ResponseBody
	public void modifyBeer(@PathVariable("commandName") String commandName, Reader reader) throws ClassNotFoundException, JsonParseException, JsonMappingException, IOException {
		final Class<?> commandType = Class.forName(String.format("org.garred.brewtour.application.command.beer.%sCommand",commandName));
		final Object command = this.objectMapper.readValue(reader, commandType);
		this.locationService.fireCommand(command);
	}

}

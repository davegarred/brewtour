package org.garred.brewtour.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Collection;

import org.garred.brewtour.application.Location;
import org.garred.brewtour.repository.LocationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

@Controller
public class LocationController extends AbstractRestController {

	private final ObjectMapper objectMapper;
	private final LocationRepository locationRepo;


	public LocationController(LocationRepository locationRepo) {
		this.locationRepo = locationRepo;
		this.objectMapper = new ObjectMapper();
		this.objectMapper.registerModule(new JSR310Module());
	}

	@RequestMapping(value = "/locations", method = GET, produces="application/json")
	@ResponseBody
	public Collection<Location> locations() throws JsonProcessingException {
		return this.locationRepo.findAll();
	}

}

package org.garred.brewtour.controller;

import static org.garred.brewtour.application.LocaleId.SEATTLE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.garred.brewtour.application.Locale;
import org.garred.brewtour.application.Location;
import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.repository.LocaleRepository;
import org.garred.brewtour.repository.LocationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LocationController extends AbstractRestController {

	private final LocaleRepository localeRepo;
	private final LocationRepository locationRepo;

	public LocationController(LocaleRepository localeRepo, LocationRepository locationRepo) {
		this.localeRepo = localeRepo;
		this.locationRepo = locationRepo;
	}

	@RequestMapping(value = "/locations", method = GET, produces="application/json")
	@ResponseBody
	public Locale locations() {
		return this.localeRepo.get(SEATTLE);
	}
	
	@RequestMapping(value = "/location/{locationId}", method = GET, produces="application/json")
	@ResponseBody
	public Location location(@PathVariable String locationId) {
		return this.locationRepo.get(new LocationId(locationId));
	}

}

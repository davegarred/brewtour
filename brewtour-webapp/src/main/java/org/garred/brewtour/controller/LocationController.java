package org.garred.brewtour.controller;

import static org.garred.brewtour.application.LocaleId.SEATTLE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.garred.brewtour.api.BeerUnavailable;
import org.garred.brewtour.application.Locale;
import org.garred.brewtour.application.Location;
import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.service.LocationQueryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LocationController extends AbstractRestController {

	private final LocationQueryService locationService;

	public LocationController(LocationQueryService locationService) {
		this.locationService = locationService;
	}

	@RequestMapping(value = "/locations", method = GET, produces="application/json")
	@ResponseBody
	public Locale locations() {
		return this.locationService.getLocale(SEATTLE);
	}

	@RequestMapping(value = "/location/{locationId}", method = GET, produces="application/json")
	@ResponseBody
	public Location location(@PathVariable String locationId) {
		return this.locationService.getLocation(new LocationId(locationId));
	}

	@RequestMapping(value = "/beerUnavailable", method = POST, produces="application/json")
	@ResponseBody
	public void addFavorite(@RequestBody BeerUnavailable beerUnavailable) {
		this.locationService.beerUnavailable(beerUnavailable);
	}
}

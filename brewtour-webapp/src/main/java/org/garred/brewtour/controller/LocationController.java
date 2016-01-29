package org.garred.brewtour.controller;

import static org.garred.brewtour.domain.LocaleId.SEATTLE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.garred.brewtour.application.command.location.AddBeerReviewCommand;
import org.garred.brewtour.application.command.location.AddLocationReviewCommand;
import org.garred.brewtour.application.command.location.BeerAvailableCommand;
import org.garred.brewtour.application.command.location.BeerUnavailableCommand;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.service.LocationQueryService;
import org.garred.brewtour.view.LocaleView;
import org.garred.brewtour.view.LocationView;
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
	public LocaleView locations() {
		final LocaleView locale = this.locationService.getLocale(SEATTLE);
		return locale;
	}

	@RequestMapping(value = "/location/{locationId}", method = GET, produces="application/json")
	@ResponseBody
	public LocationView location(@PathVariable String locationId) {
		return this.locationService.getLocation(new LocationId(locationId));
	}

	@RequestMapping(value = "/beerAvailable", method = POST, produces="application/json")
	@ResponseBody
	public void beerAvailable(@RequestBody BeerAvailableCommand beerAvailable) {
		this.locationService.fireSecuredCommand(beerAvailable);
	}

	@RequestMapping(value = "/beerUnavailable", method = POST, produces="application/json")
	@ResponseBody
	public void beerUnavailable(@RequestBody BeerUnavailableCommand beerUnavailable) {
		this.locationService.fireSecuredCommand(beerUnavailable);
	}

	@RequestMapping(value = "/locationReview", method = POST, produces="application/json")
	@ResponseBody
	public void addLocationReview(@RequestBody AddLocationReviewCommand locationReview) {
		this.locationService.fireCommand(locationReview);
	}

	@RequestMapping(value = "/beerReview", method = POST, produces="application/json")
	@ResponseBody
	public void addBeerReview(@RequestBody AddBeerReviewCommand beerReview) {
		this.locationService.fireCommand(beerReview);
	}

}

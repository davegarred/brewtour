package org.garred.brewtour.controller;

import static org.garred.brewtour.domain.LocaleId.SEATTLE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.io.Reader;

import org.garred.brewtour.application.LocationIdentifierFactoryStub;
import org.garred.brewtour.application.command.beer.AddBeerReviewCommand;
import org.garred.brewtour.application.command.location.AbstractLocationCommand;
import org.garred.brewtour.application.command.location.AddLocationCommand;
import org.garred.brewtour.application.command.location.AddLocationCommentCommand;
import org.garred.brewtour.application.command.location.AddLocationReviewCommand;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.service.LocationQueryService;
import org.garred.brewtour.service.UserDetailsService;
import org.garred.brewtour.view.LocaleView;
import org.garred.brewtour.view.LocationUserCombinedView;
import org.garred.brewtour.view.LocationView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
@RequestMapping(value = "location")
public class LocationController extends AbstractRestController {

	private final LocationQueryService locationService;
	private final LocationIdentifierFactoryStub locationIdentifierFactory;
	private final UserDetailsService userService;

	public LocationController(LocationQueryService locationService, LocationIdentifierFactoryStub locationIdentifierFactory,
			UserDetailsService userService) {
		this.locationService = locationService;
		this.locationIdentifierFactory = locationIdentifierFactory;
		this.userService = userService;
	}

	@RequestMapping(value = "/locations", method = GET, produces="application/json")
	@ResponseBody
	public LocaleView locations() {
		final LocaleView locale = this.locationService.getLocale(SEATTLE);
		return locale;
	}

	@RequestMapping(value = "{locationId}", method = GET, produces="application/json")
	@ResponseBody
	public LocationView location(@PathVariable String locationId) {
		return this.locationService.getLocation(new LocationId(locationId));
	}

//	@RequestMapping(value = "/beerAvailable", method = POST, produces="application/json")
//	@ResponseBody
//	public void beerAvailable(@RequestBody BeerAvailableCommand beerAvailable) {
//		this.locationService.fireSecuredCommand(beerAvailable);
//	}
//
//	@RequestMapping(value = "/beerUnavailable", method = POST, produces="application/json")
//	@ResponseBody
//	public void beerUnavailable(@RequestBody BeerUnavailableCommand beerUnavailable) {
//		this.locationService.fireSecuredCommand(beerUnavailable);
//	}

	@RequestMapping(value = "AddLocationComment", method = POST, produces="application/json")
	@ResponseBody
	public void addLocationComment(@RequestBody AddLocationCommentCommand locationComment) {
		this.locationService.fireCommand(locationComment);
	}

	@RequestMapping(value = "AddLocationReview", method = POST, produces="application/json")
	@ResponseBody
	public LocationUserCombinedView addLocationReview(@RequestBody AddLocationReviewCommand locationReview) {
		this.locationService.fireCommand(locationReview);
		return updatedLocationUserCombinedView(locationReview.locationId);
	}

	@RequestMapping(value = "AddBeerReview", method = POST, produces="application/json")
	@ResponseBody
	public LocationUserCombinedView addBeerReview(@RequestBody AddBeerReviewCommand beerReview, @RequestParam LocationId locationId) {
		this.locationService.fireCommand(beerReview);
		return updatedLocationUserCombinedView(locationId);
	}

	private LocationUserCombinedView updatedLocationUserCombinedView(LocationId locationId) {
		return new LocationUserCombinedView(this.locationService.getLocation(locationId), this.userService.getCurrentUserDetails());
	}

	@RequestMapping(value = "/addLocation", method = POST, produces="application/json")
	@ResponseBody
	public LocationView addlocation(@RequestBody AddLocationCommand addLocation) {
		this.locationService.fireCommand(addLocation);
		return this.locationService.getLocation(this.locationIdentifierFactory.last());
	}
	@RequestMapping(value = "/modlocation/{commandName}", method = POST, produces="application/json")
	@ResponseBody
	public LocationView modifyLocation(@PathVariable("commandName") String commandName, Reader reader) throws ClassNotFoundException, JsonParseException, JsonMappingException, IOException {
		@SuppressWarnings("unchecked")
		final Class<? extends AbstractLocationCommand> commandType = (Class<? extends AbstractLocationCommand>) Class.forName(String.format("org.garred.brewtour.application.command.location.%sCommand",commandName));
		final AbstractLocationCommand command = this.objectMapper.readValue(reader, commandType);
		this.locationService.fireCommand(command);
		return this.locationService.getLocation(command.locationId);
	}
	@RequestMapping(value = "/{commandName}", method = POST, produces="application/json")
	@ResponseBody
	public LocationView command(@PathVariable("commandName") String commandName, Reader reader) throws ClassNotFoundException, JsonParseException, JsonMappingException, IOException {
		@SuppressWarnings("unchecked")
		final Class<? extends AbstractLocationCommand> commandType = (Class<? extends AbstractLocationCommand>) Class.forName(String.format("org.garred.brewtour.application.command.location.%sCommand",commandName));
		final AbstractLocationCommand command = this.objectMapper.readValue(reader, commandType);
		this.locationService.fireCommand(command);
		return this.locationService.getLocation(command.locationId);
	}

}

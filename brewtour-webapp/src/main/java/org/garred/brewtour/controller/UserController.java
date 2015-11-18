package org.garred.brewtour.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.garred.brewtour.api.AddFavoriteLocation;
import org.garred.brewtour.api.RemoveFavoriteLocation;
import org.garred.brewtour.application.UserDetails;
import org.garred.brewtour.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController extends AbstractRestController {

	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/user", method = GET, produces="application/json")
	@ResponseBody
	public UserDetails user() {
		UserDetails details = userService.getDetails(userId());
		return details;
	}
	
	@RequestMapping(value = "/addFavorite", method = POST, produces="application/json")
	@ResponseBody
	public UserDetails addFavorite(@RequestBody AddFavoriteLocation dto) {
		return userService.addFavorite(userId(),dto.locationId);
	}
	
	@RequestMapping(value = "/removeFavorite", method = POST, produces="application/json")
	@ResponseBody
	public UserDetails removeFavorite(@RequestBody RemoveFavoriteLocation dto) {
		return userService.removeFavorite(userId(),dto.locationId);
	}

}

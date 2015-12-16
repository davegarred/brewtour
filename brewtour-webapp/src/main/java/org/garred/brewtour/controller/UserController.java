package org.garred.brewtour.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.garred.brewtour.api.AddFavoriteLocation;
import org.garred.brewtour.api.RemoveFavoriteLocation;
import org.garred.brewtour.application.UserDetails;
import org.garred.brewtour.application.UserId;
import org.garred.brewtour.config.UserHandler;
import org.garred.brewtour.service.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController extends AbstractRestController {

	private final UserDetailsService userService;

	public UserController(UserDetailsService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/user", method = GET, produces="application/json")
	@ResponseBody
	public UserDetails user() {
		final UserId userId = UserHandler.get().getIdentifier();
		final UserDetails details = this.userService.getDetails(userId);
		return details;
	}


	//TODO should be PUT
	@RequestMapping(value = "/addFavorite", method = POST, produces="application/json")
	@ResponseBody
	public UserDetails addFavorite(@RequestBody AddFavoriteLocation dto) {
		final UserId userId = UserHandler.get().getIdentifier();
		return this.userService.addFavorite(userId,dto);
	}

	@RequestMapping(value = "/removeFavorite", method = POST, produces="application/json")
	@ResponseBody
	public UserDetails removeFavorite(@RequestBody RemoveFavoriteLocation dto) {
		final UserId userId = UserHandler.get().getIdentifier();
		return this.userService.removeFavorite(userId,dto);
	}

}

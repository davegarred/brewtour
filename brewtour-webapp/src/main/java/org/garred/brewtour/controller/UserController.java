package org.garred.brewtour.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;

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
	public UserDetails user(HttpServletRequest request) {
		final UserDetails details = this.userService.getDetails(userId(request));
		return details;
	}


	@RequestMapping(value = "/addFavorite", method = POST, produces="application/json")
	@ResponseBody
	public UserDetails addFavorite(HttpServletRequest request, @RequestBody AddFavoriteLocation dto) {
		return this.userService.addFavorite(userId(request),dto);
	}

	@RequestMapping(value = "/removeFavorite", method = POST, produces="application/json")
	@ResponseBody
	public UserDetails removeFavorite(HttpServletRequest request, @RequestBody RemoveFavoriteLocation dto) {
		return this.userService.removeFavorite(userId(request),dto);
	}

}

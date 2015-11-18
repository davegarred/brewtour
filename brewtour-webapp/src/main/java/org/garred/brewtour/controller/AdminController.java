package org.garred.brewtour.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.garred.brewtour.application.UserDetails;
import org.garred.brewtour.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController extends AbstractRestController {

	private final UserService userService;
	
	public AdminController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/admin/{login}", method = GET, produces="application/json")
	@ResponseBody
	public UserDetails admin(@PathVariable("login") String login) {
		return userService.discoverUser(userId(), login, true);
	}
	
}

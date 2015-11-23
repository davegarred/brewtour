package org.garred.brewtour.controller;

import static org.garred.brewtour.filter.AuthenticationFilter.USER_ATTR;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.servlet.http.HttpServletRequest;

import org.garred.brewtour.application.UserDetails;
import org.garred.brewtour.application.UserId;
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
	public UserDetails admin(HttpServletRequest request, @PathVariable("login") UserId login) {
		final UserDetails discoveredUser = this.userService.discoverUser(login, userId(request), true, true);
		request.getSession().setAttribute(USER_ATTR, discoveredUser.getIdentifier());
		return discoveredUser;
	}

	@RequestMapping(value = "/test/{login}", method = GET, produces="application/json")
	@ResponseBody
	public UserDetails testUser(HttpServletRequest request, @PathVariable("login") UserId login) {
		final UserDetails discoveredUser = this.userService.discoverUser(login, userId(request), true, false);
		request.getSession().setAttribute(USER_ATTR, discoveredUser.getIdentifier());
		return discoveredUser;
	}

	@RequestMapping(value = "/logout", method = GET, produces="application/json")
	@ResponseBody
	public UserDetails logout(HttpServletRequest request) {
		request.getSession().removeAttribute(USER_ATTR);
		return null;
	}


}

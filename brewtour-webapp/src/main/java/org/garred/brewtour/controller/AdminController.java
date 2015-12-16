package org.garred.brewtour.controller;

import static org.garred.brewtour.application.UserAuth.ADMIN_ROLE;
import static org.garred.brewtour.application.UserAuth.TEST_ROLE;
import static org.garred.brewtour.filter.AuthenticationFilter.USER_ATTR;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.servlet.http.HttpServletRequest;

import org.garred.brewtour.application.UserAuth;
import org.garred.brewtour.application.UserId;
import org.garred.brewtour.filter.AuthenticationFilter;
import org.garred.brewtour.service.UserAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController extends AbstractRestController {

	private final UserAuthService userService;

	public AdminController(UserAuthService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/admin/{login}", method = GET, produces="application/json")
	@ResponseBody
	public UserAuth admin(HttpServletRequest request, @PathVariable("login") String login) {
		final UserAuth discoveredUser = this.userService.discoverUser(login, userId(request), ADMIN_ROLE);
		request.getSession().setAttribute(USER_ATTR, discoveredUser.getIdentifier());
		return discoveredUser;
	}

	@RequestMapping(value = "/test/{login}", method = GET, produces="application/json")
	@ResponseBody
	public UserAuth testUser(HttpServletRequest request, @PathVariable("login") String login) {
		final UserAuth discoveredUser = this.userService.discoverUser(login, userId(request), TEST_ROLE);
		request.getSession().setAttribute(USER_ATTR, discoveredUser.getIdentifier());
		return discoveredUser;
	}

	@RequestMapping(value = "/logout", method = GET, produces="application/json")
	@ResponseBody
	public UserAuth logout(HttpServletRequest request) {
		request.getSession().removeAttribute(USER_ATTR);
		return null;
	}

	protected UserId userId(HttpServletRequest request) {
		return (UserId) request.getSession().getAttribute(AuthenticationFilter.USER_ATTR);
	}

}

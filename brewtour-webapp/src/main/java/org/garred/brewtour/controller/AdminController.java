package org.garred.brewtour.controller;

import static org.garred.brewtour.filter.AuthenticationFilter.USER_ATTR;
import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;
import static org.garred.brewtour.view.UserAuthView.TEST_ROLE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.servlet.http.HttpServletRequest;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.garred.brewtour.api.AddRoleToUserCommand;
import org.garred.brewtour.api.AddUserCommand;
import org.garred.brewtour.security.UserHolder;
import org.garred.brewtour.service.UserAuthService;
import org.garred.brewtour.view.UserAuthView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController extends AbstractRestController {

	private final CommandGateway commandGateway;
	private final UserAuthService userService;


	public AdminController(CommandGateway commandGateway, UserAuthService userService) {
		this.commandGateway = commandGateway;
		this.userService = userService;
	}

	@RequestMapping(value = "/admin/{login}", method = GET, produces="application/json")
	@ResponseBody
	public UserAuthView admin(HttpServletRequest request, @PathVariable("login") String login) {
		return addUserWithRole(request, login, ADMIN_ROLE);
	}



	@RequestMapping(value = "/test/{login}", method = GET, produces="application/json")
	@ResponseBody
	public UserAuthView testUser(HttpServletRequest request, @PathVariable("login") String login) {
		return addUserWithRole(request, login, TEST_ROLE);
	}

	@RequestMapping(value = "/logout", method = GET, produces="application/json")
	@ResponseBody
	public UserAuthView logout(HttpServletRequest request) {
		request.getSession().removeAttribute(USER_ATTR);
		return null;
	}

	private UserAuthView addUserWithRole(HttpServletRequest request, String login, String role) {
		if(!UserHolder.isAuthenticated()) {
			this.commandGateway.sendAndWait(new AddUserCommand(login));
		}
		this.commandGateway.sendAndWait(new AddRoleToUserCommand(UserHolder.get().identifier(), role));
		final UserAuthView userAuth = this.userService.getCurrentUserAuth();
		UserHolder.set(userAuth);
		request.getSession().setAttribute(USER_ATTR, userAuth);
		return userAuth;
	}

}

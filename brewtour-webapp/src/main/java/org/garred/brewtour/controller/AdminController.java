package org.garred.brewtour.controller;

import static org.garred.brewtour.domain.LocaleId.SEATTLE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.garred.brewtour.application.command.user.AddRoleToUserCommand;
import org.garred.brewtour.application.command.user.AddUserCommand;
import org.garred.brewtour.repository.AdminViewRepository;
import org.garred.brewtour.security.UserHolder;
import org.garred.brewtour.security.UserNotLoggedInException;
import org.garred.brewtour.service.UserAuthService;
import org.garred.brewtour.view.AdminView;
import org.garred.brewtour.view.UserAuthView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "admin", produces="application/json")
public class AdminController extends AbstractRestController {

	private final CommandGateway commandGateway;
	private final UserAuthService userService;
	private final AdminViewRepository adminViewRepo;


	public AdminController(CommandGateway commandGateway, UserAuthService userService, AdminViewRepository adminViewRepo) {
		this.commandGateway = commandGateway;
		this.userService = userService;
		this.adminViewRepo = adminViewRepo;
	}

	@RequestMapping(method = GET)
	@ResponseBody
	public AdminView admin() {
		return this.adminViewRepo.get(SEATTLE);
	}

	@RequestMapping(value = "AddUser", method = RequestMethod.POST)
	@ResponseBody
	public UserAuthView addUser(@RequestBody AddUserCommand command) {
		this.commandGateway.sendAndWait(command);
		return this.userService.findUserByLogin(command.login);
	}


	private UserAuthView addUserWithRole(String role) {
		if(!UserHolder.isAuthenticated()) {
			throw new UserNotLoggedInException();
		}
		this.commandGateway.sendAndWait(new AddRoleToUserCommand(UserHolder.get().identifier(), role));
		final UserAuthView userAuth = this.userService.getCurrentUserAuth();
		UserHolder.update(userAuth);
		return userAuth;
	}

}

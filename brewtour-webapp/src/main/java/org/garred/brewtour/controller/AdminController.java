package org.garred.brewtour.controller;

import static org.garred.brewtour.domain.LocaleId.SEATTLE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.garred.brewtour.application.command.user.AddRoleToUserCommand;
import org.garred.brewtour.repository.AdminViewRepository;
import org.garred.brewtour.security.UserHolder;
import org.garred.brewtour.security.UserNotLoggedInException;
import org.garred.brewtour.service.UserAuthService;
import org.garred.brewtour.view.AdminView;
import org.garred.brewtour.view.UserAuthView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController extends AbstractRestController {

	private final CommandGateway commandGateway;
	private final UserAuthService userService;
	private final AdminViewRepository adminViewRepo;


	public AdminController(CommandGateway commandGateway, UserAuthService userService, AdminViewRepository adminViewRepo) {
		this.commandGateway = commandGateway;
		this.userService = userService;
		this.adminViewRepo = adminViewRepo;
	}

	@RequestMapping(value = "/admin", method = GET, produces="application/json")
	@ResponseBody
	public AdminView admin() {
		return this.adminViewRepo.get(SEATTLE);
	}

//	@RequestMapping(value = "/test", method = GET, produces="application/json")
//	@ResponseBody
//	public UserAuthView testUser() {
//		return addUserWithRole(TEST_ROLE);
//	}

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

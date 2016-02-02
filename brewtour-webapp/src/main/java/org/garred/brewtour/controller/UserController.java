package org.garred.brewtour.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.garred.brewtour.application.command.AggregateCommand;
import org.garred.brewtour.application.command.user.AddUserCommand;
import org.garred.brewtour.controller.api.UserLogin;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.security.LoginNotFoundException;
import org.garred.brewtour.security.UserHolder;
import org.garred.brewtour.service.UserDetailsService;
import org.garred.brewtour.view.UserAuthView;
import org.garred.brewtour.view.UserDetailsView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController extends AbstractRestController {

	private final UserDetailsService userService;
	private final CommandGateway commandGateway;

	public UserController(UserDetailsService userService, CommandGateway commandGateway) {
		this.userService = userService;
		this.commandGateway = commandGateway;
	}

	@RequestMapping(value = "/user", method = GET, produces="application/json")
	@ResponseBody
	public UserDetailsView user() {
		return this.userService.getCurrentUserDetails();
	}

	@RequestMapping(value = "/user/login", method = POST, produces="application/json")
	@ResponseBody
	public UserDetailsView user(@RequestBody UserLogin login) {
		final UserAuthView auth = this.userService.login(login.login, login.password);
		if(auth == null) {
			throw new LoginNotFoundException();
		}
		UserHolder.update(auth);
		return this.userService.getCurrentUserDetails();
	}

	@RequestMapping(value = "/addUser", method = GET, produces="application/json")
	@ResponseBody
	public UserDetailsView addUser() {
		return processUserCommand(new AddUserCommand(new UserId(UUID.randomUUID().toString()), "", ""));
	}


//	@RequestMapping(value = "/addFavorite", method = POST, produces="application/json")
//	@ResponseBody
//	public UserDetailsView addFavorite(@RequestBody AddFavoriteLocationCommand command) {
//		return processUserCommand(command);
//	}
//
//	@RequestMapping(value = "/removeFavorite", method = POST, produces="application/json")
//	@ResponseBody
//	public UserDetailsView removeFavorite(@RequestBody RemoveFavoriteLocationCommand command) {
//		return processUserCommand(command);
//	}

	private UserDetailsView processUserCommand(AggregateCommand<UserId> command) {
		this.commandGateway.sendAndWait(command);
		final UserDetailsView currentUserDetails = this.userService.getCurrentUserDetails();
		return currentUserDetails;
	}
}

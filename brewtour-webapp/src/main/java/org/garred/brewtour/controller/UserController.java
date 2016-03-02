package org.garred.brewtour.controller;

import static java.lang.Class.forName;
import static java.lang.String.format;
import static org.garred.brewtour.filter.AuthenticationFilter.USER_ATTR;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.io.Reader;

import javax.servlet.http.HttpServletRequest;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.garred.brewtour.controller.api.UserLogin;
import org.garred.brewtour.domain.command.user.AbstractUserFiredCommand;
import org.garred.brewtour.security.LoginNotFoundException;
import org.garred.brewtour.security.UserHolder;
import org.garred.brewtour.service.UserDetailsService;
import org.garred.brewtour.view.UserAuthView;
import org.garred.brewtour.view.UserDetailsView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
@RequestMapping(value = "user", produces="application/json")
public class UserController extends AbstractRestController {

	private final UserDetailsService userService;
	private final CommandGateway commandGateway;

	public UserController(UserDetailsService userService, CommandGateway commandGateway) {
		this.userService = userService;
		this.commandGateway = commandGateway;
	}

	@RequestMapping(method = GET)
	@ResponseBody
	public UserDetailsView user() {
		return this.userService.getCurrentUserDetails();
	}

	@RequestMapping(value = "login", method = POST)
	@ResponseBody
	public UserDetailsView user(@RequestBody UserLogin login) {
		final UserAuthView auth = this.userService.login(login.login, login.password);
		if(auth == null) {
			throw new LoginNotFoundException();
		}
		UserHolder.update(auth);
		return this.userService.getCurrentUserDetails();
	}

	@RequestMapping(value = "logout", method = GET)
	@ResponseBody
	public UserAuthView logout(HttpServletRequest request) {
		request.getSession().removeAttribute(USER_ATTR);
		UserHolder.logout();
		return null;
	}

	@RequestMapping(value = "{commandName}", method = POST, produces="application/json")
	@ResponseBody
	public UserDetailsView command(@PathVariable("commandName") String commandName, Reader reader) throws ClassNotFoundException, JsonParseException, JsonMappingException, IOException {
		@SuppressWarnings("unchecked")
		final Class<? extends AbstractUserFiredCommand> commandType = (Class<? extends AbstractUserFiredCommand>) forName(format("org.garred.brewtour.domain.command.user.%sCommand",commandName));
		final AbstractUserFiredCommand command = this.objectMapper.readValue(reader, commandType);
		this.commandGateway.sendAndWait(command);
		return this.userService.getCurrentUserDetails();
	}

}

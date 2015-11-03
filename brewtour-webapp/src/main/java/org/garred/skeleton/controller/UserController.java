package org.garred.skeleton.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.garred.skeleton.dto.UserData;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

@Controller
@RequestMapping
public class UserController extends AbstractRestController {
	
	private final ObjectMapper objectMapper;
	
	public UserController() {
		this.objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JSR310Module());
	}

	@RequestMapping(value = "/user", method = GET, produces="application/json")
	@ResponseBody
	public String user() throws JsonProcessingException {
		return objectMapper.writeValueAsString(findUser());
	}
	
	@RequestMapping(value = "/secure/login", method = GET, produces="application/json")
	@ResponseBody
	public String login() throws JsonProcessingException {
		return objectMapper.writeValueAsString(findUser());
	}
	
	@RequestMapping(value = "/secure/logout", method = POST, produces="application/json")
	@ResponseBody
	  public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		SecurityContextHolder.getContext().setAuthentication(null);
//		request.logout();
	    return "";
	  }
	
	private UserData findUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return UserData.NO_USER;
		}
		Object principal = authentication.getPrincipal();
		String username = (principal instanceof UserDetails) 
				? ((UserDetails) principal).getUsername()
				: principal.toString();
				return UserData.userData(username);
	}
}

package org.garred.brewtour.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController extends AbstractRestController {

	@RequestMapping(value = "/user", method = GET, produces="application/json")
	@ResponseBody
	public String locations() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}

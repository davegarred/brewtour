package org.garred.brewtour.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.garred.brewtour.application.UserId;

public class AuthenticationFilter implements Filter {

public static final String USER_ATTR = "userId";

//	private static final String USER_COOKIE_NAME = "user";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final HttpSession session = httpRequest.getSession();
		UserId user = (UserId) session.getAttribute(USER_ATTR);
		if(user == null) {
			user = new UserId(UUID.randomUUID().toString());
			System.out.println(user.id);
			session.setAttribute(USER_ATTR, user);
		}
		chain.doFilter(request, response);
	}



	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}

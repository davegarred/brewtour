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

import org.garred.brewtour.application.UserAuth;
import org.garred.brewtour.config.UserHandler;

public class AuthenticationFilter implements Filter {

	public static final String USER_ATTR = "userId";

//	private static final String USER_COOKIE_NAME = "user";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if(UserHandler.get() != null) {
			throw new RuntimeException("problem with my filter");
		}
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final HttpSession session = httpRequest.getSession();
		UserAuth user = (UserAuth) session.getAttribute(USER_ATTR);
		if(user == null) {
			user = UserAuth.guest(UUID.randomUUID().toString());
			session.setAttribute(USER_ATTR, user);
		}
		try {
			UserHandler.set(user);
			chain.doFilter(request, response);
		} finally {
			UserHandler.clear();
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}

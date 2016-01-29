package org.garred.brewtour.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.garred.brewtour.security.GuestUserAuth;
import org.garred.brewtour.security.UserAuth;
import org.garred.brewtour.security.UserHolder;

public class AuthenticationFilter implements Filter {

	public static final String USER_ATTR = "user";

//	private static final String USER_COOKIE_NAME = "user";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final HttpSession session = ((HttpServletRequest) request).getSession();
		UserAuth user = (UserAuth) session.getAttribute(USER_ATTR);
		// TODO add/check for cookie here first
		if(user == null) {
			user = GuestUserAuth.randomGuestAuth();
			session.setAttribute(USER_ATTR, user);
		}
		try {
			UserHolder.set(user);
			chain.doFilter(request, response);
		} finally {
			final UserAuth updatedAuth = UserHolder.clear();
			if(updatedAuth != null) {
				session.setAttribute(USER_ATTR, updatedAuth);
			}
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}

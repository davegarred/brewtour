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
import org.garred.brewtour.view.UserAuthView;

public class AuthenticationFilter implements Filter {

	public static final String USER_ATTR = "userId";

//	private static final String USER_COOKIE_NAME = "user";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final HttpSession session = ((HttpServletRequest) request).getSession();
		UserAuth user = (UserAuthView) session.getAttribute(USER_ATTR);
		if(user == null) {
			user = GuestUserAuth.randomGuestAuth();
			session.setAttribute(USER_ATTR, user);
		}
		try {
			UserHolder.set(user);
			chain.doFilter(request, response);
		} finally {
			UserHolder.clear();
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}

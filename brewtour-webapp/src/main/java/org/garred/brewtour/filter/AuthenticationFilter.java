package org.garred.brewtour.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationFilter implements Filter {

	private static final String USER_COOKIE_NAME = "user";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final SecurityContext context = SecurityContextHolder.getContext();
		final Authentication auth = context.getAuthentication();
		if(auth == null) {
			final UUID uuid = uuid(request, response);
			context.setAuthentication(new AnonymousAuthentication(uuid));
		}
		chain.doFilter(request, response);
	}

	private static UUID uuid(ServletRequest request, ServletResponse response) {
		Cookie userCookie = null;
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		if(httpRequest.getCookies() != null) {
			for(final Cookie cookie : httpRequest.getCookies()) {
				if(cookie.getName().equals(USER_COOKIE_NAME)) {
					userCookie = cookie;
				}
			}
		}

		if(userCookie != null && userCookie.getValue() != null && !userCookie.getValue().isEmpty()) {
			return UUID.fromString(userCookie.getValue());
		}
		return newUuid(response);
	}

	private static UUID newUuid(ServletResponse response) {
		final UUID uuid = UUID.randomUUID();
		final Cookie cookie = new Cookie(USER_COOKIE_NAME, uuid.toString());
		cookie.setMaxAge(-1);
		final HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.addCookie(cookie);
		return uuid;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}

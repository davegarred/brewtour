package org.garred.brewtour.filter;

import static java.util.Collections.emptyList;

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

import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final SecurityContext context = SecurityContextHolder.getContext();
		final Authentication auth = context.getAuthentication();
		if(auth == null) {
			Cookie userCookie = null;
			final HttpServletRequest httpRequest = (HttpServletRequest) request;
			final HttpServletResponse httpResponse = (HttpServletResponse) response;
			for(final Cookie cookie : httpRequest.getCookies()) {
				System.out.println(cookie.getName());
				if(cookie.getName().equals("user")) {
					userCookie = cookie;
				}
			}

			UUID uuid = null;
			if(userCookie == null) {
				uuid = UUID.randomUUID();
				userCookie = new Cookie("user", uuid.toString());
				httpResponse.addCookie(userCookie);
			} else {
				uuid = UUID.fromString(userCookie.getValue());
			}
			context.setAuthentication(new RememberMeAuthenticationToken(uuid.toString(), uuid, emptyList()));
		} else {
			System.out.println(auth.getName());
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

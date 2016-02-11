package org.garred.brewtour.security;

import java.util.Collection;

import org.garred.brewtour.domain.UserId;

public interface UserAuth {

	UserId identifier();
	String screenName();
	boolean identified();
	Collection<String> roles();

}

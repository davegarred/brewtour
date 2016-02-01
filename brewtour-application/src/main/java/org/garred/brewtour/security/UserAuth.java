package org.garred.brewtour.security;

import java.util.Collection;

import org.garred.brewtour.domain.UserId;

public interface UserAuth {

	UserId identifier();
	boolean identified();
	Collection<String> roles();

}

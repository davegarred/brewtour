package org.garred.brewtour.service;

import java.util.Set;

import org.garred.brewtour.application.UserAuth;
import org.garred.brewtour.application.UserId;

public interface UserAuthService {

	UserAuth getUserAuth(UserId userId);
	UserAuth discoverUser(String login, UserId userId, String role);
	UserAuth discoverUser(String login, UserId userId, Set<String> roles);

}

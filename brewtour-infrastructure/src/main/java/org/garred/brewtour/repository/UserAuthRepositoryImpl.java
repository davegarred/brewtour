package org.garred.brewtour.repository;

import javax.sql.DataSource;

import org.garred.brewtour.application.UserAuth;
import org.garred.brewtour.application.UserId;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserAuthRepositoryImpl extends AbstractObjectRepository<UserId,UserAuth> implements UserAuthRepository {

	public UserAuthRepositoryImpl(DataSource datasource, ObjectMapper objectMapper) {
		super("brewtour", "user_auth", UserAuth.class, datasource, objectMapper);
	}

}

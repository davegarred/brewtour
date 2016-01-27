package org.garred.brewtour.repository;

import javax.sql.DataSource;

import org.garred.brewtour.application.UserId;
import org.garred.brewtour.view.UserAuthView;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserAuthViewRepositoryImpl extends AbstractObjectRepository<UserId,UserAuthView> implements UserAuthViewRepository {

	public UserAuthViewRepositoryImpl(DataSource datasource, ObjectMapper objectMapper) {
		super("user_auth_view", UserAuthView.class, datasource, objectMapper);
	}

}

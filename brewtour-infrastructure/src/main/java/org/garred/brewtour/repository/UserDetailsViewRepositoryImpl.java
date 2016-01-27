package org.garred.brewtour.repository;

import javax.sql.DataSource;

import org.garred.brewtour.application.UserId;
import org.garred.brewtour.view.UserDetailsView;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserDetailsViewRepositoryImpl extends AbstractObjectRepository<UserId,UserDetailsView> implements UserDetailsViewRepository {

	public UserDetailsViewRepositoryImpl(DataSource datasource, ObjectMapper objectMapper) {
		super("user_details_view", UserDetailsView.class, datasource, objectMapper);
	}

}

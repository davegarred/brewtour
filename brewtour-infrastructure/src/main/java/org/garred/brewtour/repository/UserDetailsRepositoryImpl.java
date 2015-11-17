package org.garred.brewtour.repository;

import javax.sql.DataSource;

import org.garred.brewtour.application.UserDetails;
import org.garred.brewtour.application.UserId;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserDetailsRepositoryImpl extends AbstractObjectRepository<UserId,UserDetails> implements UserDetailsRepository {

	public UserDetailsRepositoryImpl(DataSource datasource, ObjectMapper objectMapper) {
		super("brewtour", "user_details", UserDetails.class, datasource, objectMapper);
	}

}

package org.garred.brewtour.repository;

import javax.sql.DataSource;

import org.garred.brewtour.domain.LocaleId;
import org.garred.brewtour.view.AdminView;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AdminViewRepositoryImpl extends AbstractViewRepository<LocaleId, AdminView> implements AdminViewRepository {

	@Autowired
	public AdminViewRepositoryImpl(DataSource datasource, ObjectMapper objectMapper) {
		super("admin_view", AdminView.class, datasource, objectMapper);
	}

}

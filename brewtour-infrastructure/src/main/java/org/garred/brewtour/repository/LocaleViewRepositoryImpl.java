package org.garred.brewtour.repository;

import javax.sql.DataSource;

import org.garred.brewtour.domain.LocaleId;
import org.garred.brewtour.view.LocaleView;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LocaleViewRepositoryImpl extends AbstractViewRepository<LocaleId, LocaleView> implements LocaleViewRepository {

	@Autowired
	public LocaleViewRepositoryImpl(DataSource datasource, ObjectMapper objectMapper) {
		super("locale_view", LocaleView.class, datasource, objectMapper);
	}

}

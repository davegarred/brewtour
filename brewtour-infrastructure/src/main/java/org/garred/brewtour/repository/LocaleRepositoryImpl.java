package org.garred.brewtour.repository;

import javax.sql.DataSource;

import org.garred.brewtour.application.Locale;
import org.garred.brewtour.application.LocaleId;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LocaleRepositoryImpl extends AbstractObjectRepository<LocaleId,Locale> implements LocaleRepository {

	public LocaleRepositoryImpl(DataSource datasource, ObjectMapper objectMapper) {
		super("brewtour", "locale", Locale.class, datasource, objectMapper);
	}

}

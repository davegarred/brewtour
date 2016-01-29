package org.garred.brewtour.repository;

import javax.sql.DataSource;

import org.garred.brewtour.domain.Entity;
import org.garred.brewtour.domain.Identifier;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractViewRepository<I extends Identifier,T extends Entity<I>> extends AbstractObjectRepository<I,T>  {

	public AbstractViewRepository(String table, Class<T> clazz, DataSource datasource,
			ObjectMapper objectMapper) {
		super(table, clazz, datasource, objectMapper);
	}

}

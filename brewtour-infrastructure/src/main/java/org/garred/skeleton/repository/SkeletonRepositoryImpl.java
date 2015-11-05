package org.garred.skeleton.repository;

import javax.sql.DataSource;

import org.garred.skeleton.api.SkelAggregate;
import org.garred.skeleton.api.SkelId;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SkeletonRepositoryImpl extends AbstractObjectRepository<SkelId,SkelAggregate> implements SkeletonRepository {


	public SkeletonRepositoryImpl(DataSource datasource, ObjectMapper objectMapper) {
		super("brewtour", "skeleton", SkelAggregate.class, datasource, objectMapper);
	}

}

package org.garred.brewtour.application;

import javax.sql.DataSource;

import org.garred.brewtour.domain.AbstractIdentifier;
import org.garred.brewtour.domain.IdentifierFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public abstract class AbstractH2IdentifierFactory<T extends AbstractIdentifier> implements IdentifierFactory<T> {

	private static final String NEXTVAL = "SELECT NEXTVAL('%s')";
	private static final String CURRVAL = "SELECT CURRVAL('%s')";

	private final JdbcTemplate jdbcTemplate;
	private final String nextVal;
	private final String currVal;

	public AbstractH2IdentifierFactory(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.nextVal = String.format(NEXTVAL, sequenceName());
		this.currVal = String.format(CURRVAL, sequenceName());
	}

	protected abstract String sequenceName();
	protected abstract T newIdentifier(long sequence);

	@Override
	public T next() {
		final SqlRowSet res = this.jdbcTemplate.queryForRowSet(this.nextVal);
		res.next();
//		return new LocationId("LOCA" + res.getInt(1));
		return newIdentifier(res.getLong(1));
	}

	@Override
	public T last() {
		final SqlRowSet res = this.jdbcTemplate.queryForRowSet(this.currVal);
		res.next();
		return newIdentifier(res.getLong(1));
	}

}

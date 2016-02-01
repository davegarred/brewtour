package org.garred.brewtour.repository;

import static java.lang.String.format;
import static java.sql.Types.CLOB;
import static java.sql.Types.INTEGER;
import static java.sql.Types.VARCHAR;
import static java.util.Arrays.asList;

import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.view.UserAuthView;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserAuthViewRepositoryImpl extends AbstractObjectRepository<UserId,UserAuthView> implements UserAuthViewRepository {

	private static final String TABLE = "user_auth_view";

	public UserAuthViewRepositoryImpl(DataSource datasource, ObjectMapper objectMapper) {
		super(TABLE, UserAuthView.class, datasource, objectMapper);
	}

	@Override
	public UserAuthView findByLogin(String login) {
		final ResultSetExtractor<UserAuthView> extractor = new ResultSetExtractor<UserAuthView>() {
			@Override
			public UserAuthView extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(!rs.next()) {
					return null;
				}
				final Reader data = rs.getCharacterStream("data");
				return deserialize(data);
			}
		};
		final PreparedStatementCreatorFactory statement = new PreparedStatementCreatorFactory(format("SELECT * FROM %s.%s WHERE login=?",SCHEMA,TABLE), VARCHAR);
		return this.jdbcTemplate.query(statement.newPreparedStatementCreator(asList(login)), extractor);
	}

	@Override
	public void save(UserAuthView value) {
		final List<Object> params = new ArrayList<>();
		final UserId key = value.identifier();
		params.add(key.getId());
		params.add(value.login);
		params.add(new Integer(1));
		params.add(serialize(value));
		final PreparedStatementCreatorFactory statement =
				new PreparedStatementCreatorFactory(format("INSERT INTO %s.%s(id,login,version,data) values (?,?,?,?)",SCHEMA,TABLE),
						VARCHAR, VARCHAR, INTEGER, CLOB);
		this.jdbcTemplate.update(statement.newPreparedStatementCreator(params));
	}

	@Override
	public void update(UserAuthView value) throws ObjectDoesNotExistException {
		int version;
		final UserId key = value.identifier();
		try {
			version = this.jdbcTemplate.queryForObject(this.findVersionQuery, Integer.class, key.getId()).intValue();
		} catch(final EmptyResultDataAccessException e) {
			throw new ObjectDoesNotExistException(this.clazz, key.getId(), e);
		}
		final List<Object> params = asList(new Integer(version+1), value.login, serialize(value), key.getId(), new Integer(version));
		final PreparedStatementCreatorFactory statement =
				new PreparedStatementCreatorFactory(format("UPDATE %s.%s SET version=?, login=?, data=? WHERE id=? AND version=?",SCHEMA,TABLE),
						INTEGER, VARCHAR, CLOB, VARCHAR, INTEGER);
		final int result = this.jdbcTemplate.update(statement.newPreparedStatementCreator(params));
		if(result != 1) {
			throw new OptimisticLockingException(this.clazz, key.getId());
		}
	}


}

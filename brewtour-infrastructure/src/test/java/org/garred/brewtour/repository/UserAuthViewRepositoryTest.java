package org.garred.brewtour.repository;

import static org.junit.Assert.assertEquals;

import javax.sql.DataSource;

import org.garred.brewtour.domain.Hash;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.view.UserAuthView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/persistence-config.xml",
		"classpath:spring/beans-config.xml",
		"classpath:spring/axon-config.xml"
})
public class UserAuthViewRepositoryTest {

	private static final String USER_LOGIN = "user login";

	private static final UserId USER_ID = new UserId("a user id");

	@Autowired
	private UserAuthViewRepository userAuthRepo;
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setDataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Before
	public void setup() {
		this.jdbcTemplate.update("DELETE FROM brewtour.location_view");
	}

	@Test
	public void test() {
		final UserAuthView view = new UserAuthView();
		view.userId = USER_ID;
		view.login = USER_LOGIN;
		view.hash = Hash.hashFromPassword(USER_ID, "password");
		this.userAuthRepo.save(view);
		final UserAuthView result = this.userAuthRepo.get(USER_ID);
		assertEquals(USER_LOGIN, result.login);

		final UserAuthView find = this.userAuthRepo.findByLogin(USER_LOGIN);
		assertEquals(USER_ID, find.userId);
	}


}

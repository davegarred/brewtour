package org.garred.brewtour.repository;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.emptyList;
import static org.garred.brewtour.application.AvailableImages.NO_IMAGES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.sql.DataSource;

import org.garred.brewtour.application.Location;
import org.garred.brewtour.application.LocationId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-config.xml")
public class LocationRepositoryTest {

	private static final LocationId LOCATION_ID = new LocationId("someKey");
	private static final Location LOCATION = new Location(LOCATION_ID, "", "", "", ZERO, ZERO, NO_IMAGES, emptyList());
	private static final Location LOCATION_2 = new Location(LOCATION_ID, "", "", "", ONE, ONE, NO_IMAGES, emptyList());

	@Autowired
	private LocationRepository locationRepo;
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setDataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Before
	public void setup() {
		this.jdbcTemplate.update("DELETE FROM brewtour.location");
	}

	@Test
	public void testSave() {
		this.locationRepo.save(LOCATION_ID, LOCATION);
		final Location result = this.locationRepo.get(LOCATION_ID);
		assertEquals(ZERO, result.latitude);
	}

	@Test
	public void testExists() {
		this.locationRepo.save(LOCATION_ID, LOCATION);
		final boolean result = this.locationRepo.exists(LOCATION_ID);
		assertTrue(result);
	}

	@Test
	public void testExists_false() {
		final boolean result = this.locationRepo.exists(LOCATION_ID);
		assertFalse(result);
	}

	@Test
	public void testUpdate() {
		this.locationRepo.save(LOCATION_ID, LOCATION);

		this.locationRepo.update(LOCATION_ID, LOCATION_2);
		final Location result = this.locationRepo.get(LOCATION_ID);
		assertEquals(ONE, result.latitude);
	}

	@Test(expected = ObjectDoesNotExistException.class)
	public void testUpdate_doesNotExist() {
		this.locationRepo.update(LOCATION_ID, LOCATION);
	}

	@Test
	public void testDelete() {
		this.locationRepo.save(LOCATION_ID, LOCATION);
		this.locationRepo.delete(LOCATION_ID);
		assertNull(this.locationRepo.get(LOCATION_ID));
	}

}

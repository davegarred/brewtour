package org.garred.brewtour.repository;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.emptyList;
import static org.garred.brewtour.domain.AvailableImages.NO_IMAGES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import javax.sql.DataSource;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.view.LocationView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-config.xml")
public class LocationViewRepositoryTest {

	private static final LocationId LOCATION_ID = new LocationId("someKey");
	private static final LocationView LOCATION = new LocationView();
	private static final LocationView LOCATION_2 = new LocationView();

	static {
		LOCATION.locationId = LOCATION_ID;
		LOCATION.name = "";
		LOCATION.description = "";
		LOCATION.latitude = ZERO;
		LOCATION.longitude = ZERO;
		LOCATION.images = NO_IMAGES;
		LOCATION.reviews = emptyList();
		LOCATION.beers = new HashMap<>();

		LOCATION_2.locationId = LOCATION_ID;
		LOCATION_2.name = "";
		LOCATION_2.description = "";
		LOCATION_2.latitude = ONE;
		LOCATION_2.longitude = ONE;
		LOCATION_2.images = NO_IMAGES;
		LOCATION_2.reviews = emptyList();
		LOCATION_2.beers = new HashMap<>();
	}

	@Autowired
	private LocationViewRepository locationRepo;
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
	public void testSave() {
		this.locationRepo.save(LOCATION);
		final LocationView result = this.locationRepo.get(LOCATION_ID);
		assertEquals(ZERO, result.latitude);
	}

	@Test
	public void testRequire() throws ObjectDoesNotExistException {
		this.locationRepo.save(LOCATION);
		final LocationView result = this.locationRepo.require(LOCATION_ID);
		assertEquals(ZERO, result.latitude);
	}

	@Test(expected = ObjectDoesNotExistException.class)
	public void testRequire_doesNotExist() throws ObjectDoesNotExistException {
		this.locationRepo.require(LOCATION_ID);
	}

	@Test
	public void testExists() {
		this.locationRepo.save(LOCATION);
		final boolean result = this.locationRepo.exists(LOCATION_ID);
		assertTrue(result);
	}

	@Test
	public void testExists_false() {
		final boolean result = this.locationRepo.exists(LOCATION_ID);
		assertFalse(result);
	}

	@Test
	public void testUpdate() throws ObjectDoesNotExistException {
		this.locationRepo.save(LOCATION);

		this.locationRepo.update(LOCATION_2);
		final LocationView result = this.locationRepo.get(LOCATION_ID);
		assertEquals(ONE, result.latitude);
	}

	@Test(expected = ObjectDoesNotExistException.class)
	public void testUpdate_doesNotExist() throws ObjectDoesNotExistException {
		this.locationRepo.update(LOCATION);
	}

	@Test
	public void testDelete() {
		this.locationRepo.save(LOCATION);
		this.locationRepo.delete(LOCATION_ID);
		assertNull(this.locationRepo.get(LOCATION_ID));
	}

}

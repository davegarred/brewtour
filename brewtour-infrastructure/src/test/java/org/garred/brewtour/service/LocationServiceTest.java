package org.garred.brewtour.service;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static org.garred.brewtour.application.UserAuth.ADMIN_ROLE;
import static org.garred.brewtour.application.UserAuth.TEST_ROLE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

import org.garred.brewtour.api.ModifyLocationDescription;
import org.garred.brewtour.application.AvailableImages;
import org.garred.brewtour.application.Image;
import org.garred.brewtour.application.Location;
import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.application.UserAuth;
import org.garred.brewtour.application.UserDetails;
import org.garred.brewtour.application.UserId;
import org.garred.brewtour.config.SecureAspect;
import org.garred.brewtour.config.UserHandler;
import org.garred.brewtour.repository.LocaleRepository;
import org.garred.brewtour.repository.LocationRepository;
import org.garred.brewtour.repository.UserDetailsRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

public class LocationServiceTest {

	private static final UserId USER_ID = new UserId("some user");
	private static final UserId USER_ID_2 = new UserId("some other user");
	private static final UserAuth ADMIN_USER = UserAuth.userAuthorization(USER_ID, "", new HashSet<>(asList(ADMIN_ROLE)));
	private static final UserAuth TEST_USER = UserAuth.userAuthorization(USER_ID_2, "", new HashSet<>(asList(TEST_ROLE)));
	private static final UserAuth GUEST_USER = UserAuth.guest(USER_ID_2.id);
	private static final String LOCATION_DESCRIPTION = "some interesting description";
	private static final String LOCATION_DESCRIPTION_2 = "a less interesting description of some (or another) location";
	private static final LocationId LOCATION_ID = new LocationId("a location id");
	private static final LocationId LOCATION_ID_2 = new LocationId("another location id");
	private static final Image IMAGE_2 = new Image("image 2");
	private static final Image IMAGE_1 = new Image("image 1");

	private static Location location(LocationId locationId) {
		return new Location(locationId, null, "a location name", LOCATION_DESCRIPTION,
				new BigDecimal("47.614"), new BigDecimal("-122.315"),
				new AvailableImages(IMAGE_1, IMAGE_2, null), new ArrayList<>(), new ArrayList<>());
	}
	private LocationRepository locationRepo;
	private LocaleRepository localeRepo;
	private UserDetailsRepository userDetailsRepo;
	private LocationService locationService;

	@Before
	public void setup() {
		this.locationRepo = new LocationRepositoryStub();
		this.localeRepo = new LocaleRepositoryStub();
		this.userDetailsRepo = new UserDetailsRepositoryStub();
		this.userDetailsRepo.save(new UserDetails(USER_ID, emptySet()));
		this.locationService = configureSecureAspect(new LocationServiceImpl(this.locationRepo, this.localeRepo));

		this.locationRepo.save(location(LOCATION_ID));
		this.locationRepo.save(location(LOCATION_ID_2));
		UserHandler.set(ADMIN_USER);
	}

	private static <T> T configureSecureAspect(T target) {
		final AspectJProxyFactory factory = new AspectJProxyFactory(target);
		factory.addAspect(new SecureAspect());
		return factory.getProxy();
	}

	//*******************************************************
	//   security
	//*******************************************************
	@Test(expected = RuntimeException.class)
	public void testNoPermissions() {
		UserHandler.set(GUEST_USER);
		this.locationService.modifyLocationDescription(new ModifyLocationDescription(LOCATION_ID, LOCATION_DESCRIPTION_2));
	}
	@Test
	public void testAdminOnly() {
		UserHandler.set(ADMIN_USER);
		this.locationService.modifyLocationDescription(new ModifyLocationDescription(LOCATION_ID, LOCATION_DESCRIPTION_2));
	}
	@Test
	public void testTestUserOnly() {
		UserHandler.set(TEST_USER);
		this.locationService.modifyLocationDescription(new ModifyLocationDescription(LOCATION_ID, LOCATION_DESCRIPTION_2));
	}

}

package org.garred.brewtour.application;

import javax.sql.DataSource;

import org.garred.brewtour.domain.LocationId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/persistence-config.xml")
public class H2LocationIdentifierFactoryTest {

	private IdentifierFactory<LocationId> locationIdentifierFactory;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.locationIdentifierFactory = new H2LocationIdentifierFactory(dataSource);
	}

	@Test
	public void testSeq() {
		for(int i=1; i<100; i++) {
			final LocationId next = this.locationIdentifierFactory.next();
			Assert.assertEquals(new LocationId("LOCA10" + zeroPad(i) + i), next);
			final LocationId validateLast = this.locationIdentifierFactory.last();
			Assert.assertEquals(next, validateLast);
		}
	}

	private static String zeroPad(int number) {
		final StringBuilder builder = new StringBuilder();
		final int neededZeros = number < 10 ? 3 : number < 100 ? 2 : 3;
		for(int i=0; i<neededZeros; i++) {
			builder.append("0");
		}
		return builder.toString();
	}

}

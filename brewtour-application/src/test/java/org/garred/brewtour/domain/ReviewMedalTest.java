package org.garred.brewtour.domain;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.garred.brewtour.domain.ReviewMedal.BRONZE;
import static org.garred.brewtour.domain.ReviewMedal.GOLD;
import static org.garred.brewtour.domain.ReviewMedal.NONE;
import static org.garred.brewtour.domain.ReviewMedal.SILVER;
import static org.garred.brewtour.domain.ReviewMedal.average;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class ReviewMedalTest {

	@Test
	public void testMedals() {
		assertEquals(GOLD, average(asList(GOLD), emptyList()));
		assertEquals(GOLD, average(asList(GOLD, SILVER), emptyList()));
		assertEquals(SILVER, average(asList(GOLD, SILVER, SILVER), emptyList()));
		assertEquals(SILVER, average(asList(GOLD, SILVER, BRONZE), emptyList()));
		assertEquals(SILVER, average(asList(GOLD, SILVER, BRONZE, NONE), emptyList()));
		assertEquals(BRONZE, average(asList(GOLD, SILVER, BRONZE, NONE, NONE), emptyList()));
	}

	@Test
	public void testOutsideRatings() {
		assertEquals(GOLD, average(emptyList(), asList(new BigDecimal("0.9"))));
		assertEquals(SILVER, average(emptyList(), asList(new BigDecimal("0.89"))));
		assertEquals(SILVER, average(emptyList(), asList(new BigDecimal("0.80"))));
		assertEquals(BRONZE, average(emptyList(), asList(new BigDecimal("0.79"))));
		assertEquals(BRONZE, average(emptyList(), asList(new BigDecimal("0.70"))));
		assertEquals(NONE, average(emptyList(), asList(new BigDecimal("0.69"))));

		assertEquals(SILVER, average(emptyList(), asList(new BigDecimal("0.9"),new BigDecimal("0.8"))));
	}

	@Test
	public void testCombined() {
		assertEquals(GOLD, average(asList(GOLD), asList(new BigDecimal("0.9"))));
		assertEquals(GOLD, average(asList(GOLD), asList(new BigDecimal("0.89"))));
		assertEquals(SILVER, average(asList(GOLD), asList(new BigDecimal("0.80"))));
		assertEquals(SILVER, average(asList(GOLD), asList(new BigDecimal("0.79"))));
		assertEquals(SILVER, average(asList(SILVER), asList(new BigDecimal("0.79"))));
		assertEquals(SILVER, average(asList(GOLD), asList(new BigDecimal("0.70"))));
		assertEquals(BRONZE, average(asList(SILVER), asList(new BigDecimal("0.70"))));
		assertEquals(BRONZE, average(asList(BRONZE), asList(new BigDecimal("0.70"))));
		assertEquals(NONE, average(emptyList(), asList(new BigDecimal("0.69"))));
	}
}

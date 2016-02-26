package org.garred.brewtour.domain;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;
import java.util.Collection;

public enum ReviewMedal {
	GOLD,
	SILVER,
	BRONZE,
	NONE;

	private static final BigDecimal GRADE_A = new BigDecimal(".90");
	private static final BigDecimal GRADE_B = new BigDecimal(".80");
	private static final BigDecimal GRADE_C = new BigDecimal(".70");
	private static final BigDecimal GRADE_F = new BigDecimal(".50");

	private static final BigDecimal WEIGHT_GOLD = new BigDecimal(".95");
	private static final BigDecimal WEIGHT_SILVER = new BigDecimal(".85");
	private static final BigDecimal WEIGHT_BRONZE = new BigDecimal(".75");
	private static final BigDecimal WEIGHT_NONE = new BigDecimal(".65");

	public static ReviewMedal average(Collection<ReviewMedal> allMedals, Collection<BigDecimal> outsideRatings) {
		final BigDecimal outsideRating = outsideRatingAverage(outsideRatings);
		final BigDecimal avg = internalRatingAverage(allMedals);
		if(outsideRating == null && avg == null) {
			return null;
		} else if(outsideRating == null) {
			return medalFor(avg);
		} else if(avg == null) {
			return medalFor(outsideRating);
		}
		final BigDecimal averageRating = outsideRating.add(avg).divide(new BigDecimal(2), HALF_UP);
		return medalFor(averageRating);
	}

	private static ReviewMedal medalFor(BigDecimal value) {
		if(value.compareTo(GRADE_A) >= 0) {
			return GOLD;
		} else if(value.compareTo(GRADE_B) >= 0) {
			return SILVER;
		} else if(value.compareTo(GRADE_C) >= 0) {
			return BRONZE;
		}
		return NONE;
	}

	private static BigDecimal internalRatingAverage(Collection<ReviewMedal> medals) {
		if(medals.isEmpty()) {
			return null;
		}
		BigDecimal sum = BigDecimal.ZERO;
		for(final ReviewMedal medal : medals) {
			switch(medal) {
			case GOLD:
				sum = sum.add(WEIGHT_GOLD);
				break;
			case SILVER:
				sum = sum.add(WEIGHT_SILVER);
				break;
			case BRONZE:
				sum = sum.add(WEIGHT_BRONZE);
				break;
			case NONE:
				sum = sum.add(WEIGHT_NONE);
			}
		}
		return sum.divide(new BigDecimal(medals.size()), HALF_UP);
	}

	private static BigDecimal outsideRatingAverage(Collection<BigDecimal> outsideRatings) {
		if(outsideRatings.isEmpty()) {
			return null;
		}
		BigDecimal result = ZERO.setScale(2);
		int count = 0;
		for(final BigDecimal rating : outsideRatings) {
			result = result.add(rating.setScale(2, HALF_UP));
			count++;
		}
		return result.divide(new BigDecimal(count), HALF_UP);
	}
}

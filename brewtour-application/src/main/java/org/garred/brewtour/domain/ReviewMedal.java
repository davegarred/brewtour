package org.garred.brewtour.domain;

import java.util.List;

public enum ReviewMedal {
	GOLD,
	SILVER,
	BRONZE,
	NONE;

	public static ReviewMedal average(List<ReviewMedal> allMedals) {
		if(allMedals.isEmpty()) {
			return NONE;
		}
		int sum = 0;
		for(final ReviewMedal medal : allMedals) {
			switch(medal) {
			case GOLD:
				sum++;
			case SILVER:
				sum++;
			case BRONZE:
				sum++;
			case NONE:
			}
		}
		final double avg = ((double)sum) / ((double)allMedals.size());
		return avg > 2.5 ? GOLD : avg > 1.5 ? SILVER : BRONZE;
	}
}

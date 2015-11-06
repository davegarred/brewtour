package org.garred.brewtour.brewdb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

public class BrewDbLocation {

	public String id, breweryId, breweryName, breweryDescription;
	public Map<String,String> images;
	public String[] beerIds;
	public BigDecimal latitude, longitude;

	@SuppressWarnings("serial")
	public static class BrewDbLocationList extends ArrayList<BrewDbLocation>{}
}

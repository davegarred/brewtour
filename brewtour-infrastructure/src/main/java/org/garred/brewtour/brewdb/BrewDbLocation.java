package org.garred.brewtour.brewdb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrewDbLocation {

	public String id, name, streetAddress, website, locality, region, postalCode, phone, hoursOfOperation, status, breweryId;
	public String[] beerIds;
	public BigDecimal latitude, longitude;
	public BrewDbBrewery brewery;
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class BrewDbBrewery {
	    public String id, name, description, website;
	    public Map<String,String> images;
	}

	@SuppressWarnings("serial")
	public static class BrewDbLocationList extends ArrayList<BrewDbLocation>{}
}

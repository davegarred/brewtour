package org.garred.brewtour.config;

import java.util.ArrayList;
import java.util.List;

public class BreweryData {
	public String breweryId;
	public String breweryName;
	public List<String> locationIds = new ArrayList<>();

	public BreweryData() {}
	public BreweryData(String breweryId, String breweryName) {
		this.breweryId = breweryId;
		this.breweryName = breweryName;
	}

}

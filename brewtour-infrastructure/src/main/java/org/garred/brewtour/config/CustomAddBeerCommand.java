package org.garred.brewtour.config;

import org.garred.brewtour.domain.command.beer.AddBeerCommand;

public class CustomAddBeerCommand {
	public String locationName;
	public String breweryName;
	public AddBeerCommand command;
	
	public static CustomAddBeerCommand genericCustomBeerCommand(String locationBreweryName, AddBeerCommand command) {
		CustomAddBeerCommand cust = new CustomAddBeerCommand();
		cust.locationName = locationBreweryName;
		cust.breweryName = locationBreweryName;
		cust.command = command;
		return cust;
	}
}
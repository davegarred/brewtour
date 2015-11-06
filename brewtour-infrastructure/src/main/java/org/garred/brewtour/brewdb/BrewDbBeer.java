package org.garred.brewtour.brewdb;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrewDbBeer {

	public String id, name, description,isOrganic,status,statusDisplay;
	public String createDate,updateDate;
	public int styleId;
	public BigDecimal abv,ibu;
	public Style style;

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Style {
		public int id,categoryId;
		public String name,shortName,description;
		public String createDate,updateDate;
		public BigDecimal ibuMin,ibuMax,abvMin,abvMax,srmMin,srmMax,ogMin,ogMax,fgMin,fgMax;
		public Category category;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Category {
		public int id;
		public String name,description;
		public String createDate;
	}

	@SuppressWarnings("serial")
	public static class BrewDbBeerList extends ArrayList<BrewDbBeer>{}
}

package org.garred.brewdb.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Beer extends AbstractReadableObject {

	public String breweryId;

	public String id,name,description,abv,ibu,isOrganic,status,statusDisplay,foodPairings;
	public Integer availableId,styleId,glasswareId,srmId,year;
	public LocalDateTime createDate,updateDate;
	public Images labels;
	public BeerStyle style;
	public Availability available;
	public BeerGlass glass;
	public BeerSrm srm;

}

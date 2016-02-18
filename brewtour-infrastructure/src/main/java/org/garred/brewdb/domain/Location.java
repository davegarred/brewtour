package org.garred.brewdb.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location extends AbstractReadableObject {

	public String id,name,streetAddress,locality,region,postalCode,phone,website,hoursOfOperation,
	isPrimary,inPlanning,isClosed,openToPublic,locationType,locationTypeDisplay,countryIsoCode,yearOpen,
	status,statusDisplay,breweryId;
	public LocalDateTime createDate,updateDate;
	public BigDecimal latitude,longitude;
	public Brewery brewery;
	public Country country;

}

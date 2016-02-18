package org.garred.brewdb.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Country extends AbstractReadableObject {

	public String isoCode,name,displayName,isoThree;
	public int numberCode;
	public LocalDateTime createDate;
	
}

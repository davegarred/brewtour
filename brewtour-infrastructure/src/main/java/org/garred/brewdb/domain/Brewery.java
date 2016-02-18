package org.garred.brewdb.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Brewery extends AbstractReadableObject {

	public String id,name,description,website,established,isOrganic,status,statusDisplay;
	public LocalDateTime createDate,updateDate;
	public Images images;
	
}

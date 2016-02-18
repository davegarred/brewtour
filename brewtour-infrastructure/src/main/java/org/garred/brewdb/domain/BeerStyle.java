package org.garred.brewdb.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BeerStyle extends AbstractReadableObject {

	public int id,categoryId;
	public String name,shortName,description,ibuMin,ibuMax,abvMin,abvMax,srmMin,srmMax,ogMin,ogMax,fgMin,fgMax;
	public LocalDateTime createDate,updateDate;
	public BeerCategory category;
	
}

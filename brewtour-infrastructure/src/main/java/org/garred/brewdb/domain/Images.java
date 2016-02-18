package org.garred.brewdb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Images extends AbstractReadableObject {

	public String icon,medium,large;
}

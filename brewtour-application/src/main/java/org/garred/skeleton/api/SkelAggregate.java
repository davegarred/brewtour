package org.garred.skeleton.api;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SkelAggregate {

	public final SkelId id;
	public final List<String> values;

	public SkelAggregate(SkelId id) {
		this.id = id;
		this.values =  new ArrayList<>();
	}
	
	@JsonCreator
	public SkelAggregate(@JsonProperty("id") SkelId id, 
			@JsonProperty("values") List<String> values) {
		this.id = id;
		this.values = values;
	}
	
	public void addValue(String value) {
		values.add(value);
	}
	
}

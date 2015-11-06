package org.garred.brewtour.application;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AbstractObject {

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,SHORT_PREFIX_STYLE);
	}

}

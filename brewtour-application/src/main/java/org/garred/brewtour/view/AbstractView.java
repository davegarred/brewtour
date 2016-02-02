package org.garred.brewtour.view;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AbstractView {

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	@Override
	public boolean equals(Object that) {
		return EqualsBuilder.reflectionEquals(this, that, true);
	}
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, true);
	}
}

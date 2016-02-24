package org.garred.brewtour.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public abstract class AbstractIdentifier extends AbstractObject implements Identifier {

	public final String id;

	@JsonCreator
	public AbstractIdentifier(String id) {
		this.id = id;
	}

	@Override
	@JsonValue
	public String getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return this.id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AbstractIdentifier other = (AbstractIdentifier) obj;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		return true;
	}
}

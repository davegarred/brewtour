package org.garred.skeleton.api.command;

import org.garred.skeleton.api.SkelId;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddValueSkelCommand extends AbstractSkelCommand {

	@NotEmpty
	public final String value;
	
	@JsonCreator
	public AddValueSkelCommand(@JsonProperty("id") SkelId id, @JsonProperty("value") String value) {
		super(id);
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddValueSkelCommand other = (AddValueSkelCommand) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	
}

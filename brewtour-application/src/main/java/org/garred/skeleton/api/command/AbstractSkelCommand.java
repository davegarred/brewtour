package org.garred.skeleton.api.command;

import org.garred.skeleton.api.SkelId;

import com.nullgeodesic.cqrs.api.Command;

public class AbstractSkelCommand implements Command<SkelId> {

	public final SkelId id;

	public AbstractSkelCommand(SkelId id) {
		this.id = id;
	}

	@Override
	public SkelId aggregateId() {
		return id;
	}

	@Override
	public Class<SkelId> aggregateIdType() {
		return SkelId.class;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		AbstractSkelCommand other = (AbstractSkelCommand) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}

package org.garred.brewtour.application;

public abstract class AbstractEntity<I> extends AbstractObject implements Entity<I> {

	protected final I identifier;

	public AbstractEntity(I identifier) {
		this.identifier = identifier;
	}

	@Override
	public I getIdentifier() {
		return this.identifier;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.identifier == null) ? 0 : this.identifier.hashCode());
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
		@SuppressWarnings("unchecked")
		final
		AbstractEntity<I> other = (AbstractEntity<I>) obj;
		if (this.identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!this.identifier.equals(other.identifier))
			return false;
		return true;
	}
}

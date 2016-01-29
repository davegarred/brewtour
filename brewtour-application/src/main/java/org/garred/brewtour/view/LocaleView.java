package org.garred.brewtour.view;

import java.util.List;

import org.garred.brewtour.domain.Entity;
import org.garred.brewtour.domain.GoogleMapsParameters;
import org.garred.brewtour.domain.LocaleId;
import org.garred.brewtour.domain.LocalePoint;

public class LocaleView implements Entity<LocaleId> {

	public LocaleId id;
	public String name;
	public GoogleMapsParameters googleMapsParameters;
	public List<LocalePoint> locations;

	@Override
	public LocaleId identifier() {
		return this.id;
	}

}

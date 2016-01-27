package org.garred.brewtour.view;

import java.util.List;

import org.garred.brewtour.application.Entity;
import org.garred.brewtour.application.GoogleMapsParameters;
import org.garred.brewtour.application.LocaleId;
import org.garred.brewtour.application.LocalePoint;

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

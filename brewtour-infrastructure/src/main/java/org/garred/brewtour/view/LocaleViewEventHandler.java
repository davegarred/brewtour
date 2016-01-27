package org.garred.brewtour.view;

import static org.garred.brewtour.application.LocaleId.SEATTLE;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.garred.brewtour.application.GoogleMapsParameters;
import org.garred.brewtour.application.GoogleMapsParameters.GoogleMapsPosition;
import org.garred.brewtour.application.LocaleId;
import org.garred.brewtour.application.LocalePoint;
import org.garred.brewtour.application.event.AbstractLocationAddedEvent;
import org.garred.brewtour.repository.LocaleViewRepository;

public class LocaleViewEventHandler extends AbstractViewEventHandler<LocaleId, LocaleView> {

	public LocaleViewEventHandler(LocaleViewRepository repository) {
		super(repository);
	}

	@EventHandler
    public void on(AbstractLocationAddedEvent event) {
		final LocaleView locale = getOrInitialize();
		locale.locations.add(new LocalePoint(event.locationId, event.name, event.latitude, event.longitude, event.images));
		persist(locale);
    }

	private LocaleView getOrInitialize() {
		LocaleView locale = this.repository.get(SEATTLE);
		if(locale == null) {
			locale = new LocaleView();
			locale.id = SEATTLE;
			locale.name = SEATTLE.id;
			locale.googleMapsParameters = new GoogleMapsParameters(new GoogleMapsPosition(new BigDecimal("47.61"), new BigDecimal("-122.333")),
					12);
			locale.locations = new ArrayList<>();
			this.repository.save(locale);
		}
		return locale;
	}

}

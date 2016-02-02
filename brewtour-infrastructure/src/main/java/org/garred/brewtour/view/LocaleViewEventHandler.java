package org.garred.brewtour.view;

import static org.garred.brewtour.domain.LocaleId.SEATTLE;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.garred.brewtour.application.event.location.LocationAddedEvent;
import org.garred.brewtour.application.event.location.LocationImagesUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationPositionUpdatedEvent;
import org.garred.brewtour.domain.GoogleMapsParameters;
import org.garred.brewtour.domain.GoogleMapsParameters.GoogleMapsPosition;
import org.garred.brewtour.domain.LocaleId;
import org.garred.brewtour.domain.LocalePoint;
import org.garred.brewtour.repository.LocaleViewRepository;

public class LocaleViewEventHandler extends AbstractViewEventHandler<LocaleId, LocaleView> {

	public LocaleViewEventHandler(LocaleViewRepository repository) {
		super(repository);
	}

	@EventHandler
	public void on(LocationAddedEvent event) {
		final LocaleView locale = getOrInitialize();
		locale.locations.add(new LocalePoint(event.locationId, event.name, null, null, null));
		persist(locale);
	}

	@EventHandler
	public void on(LocationPositionUpdatedEvent event) {
		final LocaleView locale = this.repository.get(SEATTLE);
		for(final LocalePoint location : locale.locations) {
			if(location.locationId.equals(event.locationId)) {
				location.latitude = event.latitude;
				location.longitude = event.longitude;
				persist(locale);
				return;
			}
		}
	}
	@EventHandler
	public void on(LocationImagesUpdatedEvent event) {
		final LocaleView locale = this.repository.get(SEATTLE);
		for(final LocalePoint location : locale.locations) {
			if(location.locationId.equals(event.locationId)) {
				location.images = event.images;
				persist(locale);
				return;
			}
		}
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

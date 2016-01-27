package org.garred.brewtour.service;

import org.garred.brewtour.application.LocaleId;
import org.garred.brewtour.repository.LocaleViewRepository;
import org.garred.brewtour.view.LocaleView;

public class LocaleViewRepositoryStub extends AbstractRepositoryStub<LocaleId, LocaleView> implements LocaleViewRepository {

	@Override
	protected Class<LocaleView> objectClass() {
		return LocaleView.class;
	}

}

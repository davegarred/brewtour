package org.garred.brewtour.service;

import org.garred.brewtour.application.UserId;
import org.garred.brewtour.repository.UserDetailsViewRepository;
import org.garred.brewtour.view.UserDetailsView;

public class UserDetailsViewRepositoryStub extends AbstractRepositoryStub<UserId, UserDetailsView> implements UserDetailsViewRepository {

	@Override
	protected Class<UserDetailsView> objectClass() {
		return UserDetailsView.class;
	}

}

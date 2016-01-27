package org.garred.brewtour.service;

public interface LocationService {

	void fireCommand(Object command);
	void fireSecuredCommand(Object command);

}

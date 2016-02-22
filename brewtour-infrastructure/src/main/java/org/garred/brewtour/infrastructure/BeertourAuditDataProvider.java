package org.garred.brewtour.infrastructure;

import java.util.Map;

import org.axonframework.auditing.AuditDataProvider;
import org.axonframework.commandhandling.CommandMessage;

public class BeertourAuditDataProvider implements AuditDataProvider {

	@Override
	public Map<String, Object> provideAuditDataFor(CommandMessage<?> command) {
		return command.getMetaData();
	}

}

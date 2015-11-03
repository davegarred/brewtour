package org.garred.skeleton;

import org.garred.skeleton.api.SkelId;
import org.garred.skeleton.api.command.AddSkelCommand;
import org.garred.skeleton.api.command.AddValueSkelCommand;
import org.garred.skeleton.api.event.SkelAddedEvent;
import org.garred.skeleton.api.event.SkelValueAddedEvent;

import com.google.common.eventbus.EventBus;
import com.nullgeodesic.cqrs.CommandBus;
import com.nullgeodesic.cqrs.CommandHandler;
import com.nullgeodesic.cqrs.CommandEndpoint;

public class SkelCommandHandler extends CommandHandler<SkelId> {

	public SkelCommandHandler(CommandBus commandBus, EventBus eventBus) {
		super(SkelId.class, commandBus, eventBus);
	}
	
	@CommandEndpoint
	public void on(AddSkelCommand command) {
		apply(new SkelAddedEvent(command.id));
	}
	
	@CommandEndpoint
	public void on(AddValueSkelCommand command) {
		apply(new SkelValueAddedEvent(command.id, command.value));
	}

}

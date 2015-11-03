package org.garred.skeleton.infrastructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.eventbus.EventBus;
import com.nullgeodesic.cqrs.CommandBus;
import com.nullgeodesic.cqrs.CommandHandler;
import com.nullgeodesic.cqrs.api.Command;

public class CommandHandlerStub<I> extends CommandHandler<I> {

	private final List<Command<I>> commands = new ArrayList<>();
	
	public CommandHandlerStub(Class<I> aggregateIdentifier, CommandBus commandBus, EventBus eventBus) {
		super(aggregateIdentifier, commandBus, eventBus);
	}
	
	@Override
	public void handle(Command<I> command) {
		commands.add(command);
	}
	
	public Collection<Command<I>> commands() {
		return commands;
	}

}

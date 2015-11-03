package org.garred.skeleton.infrastructure;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.nullgeodesic.cqrs.api.Message;
import com.nullgeodesic.cqrs.logging.GenericLoggedMessage;
import com.nullgeodesic.cqrs.logging.LoggedMessage;
import com.nullgeodesic.cqrs.logging.MessageLogger;

public class HashMapMessageLogger implements MessageLogger {

	private final Multimap<Object, LoggedMessage<?>> messageLogByAggId = ArrayListMultimap.create();
	private final List<LoggedMessage<?>> messageLog = new ArrayList<>();
	
	@Override
	public <I> void log(Message<I> message) {
		LoggedMessage<?> loggedMessage = new GenericLoggedMessage<I>(message, ZonedDateTime.now());
		messageLogByAggId.put(message.aggregateId(), loggedMessage);
		messageLog.add(loggedMessage);
	}

	@Override
	public Iterator<LoggedMessage<?>> replay(Object aggregateIdentifier) {
		return messageLogByAggId.get(aggregateIdentifier).iterator();
	}

	@Override
	public Iterator<LoggedMessage<?>> replay() {
		return new ArrayList<>(messageLog).iterator();
	}

}

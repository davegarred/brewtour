package org.garred.skeleton.infrastructure;

import static org.junit.Assert.assertEquals;

import java.time.Period;
import java.util.Collection;

import org.garred.skeleton.api.SkelId;
import org.garred.skeleton.api.command.AddSkelCommand;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.eventbus.EventBus;
import com.nullgeodesic.cqrs.CommandBus;
import com.nullgeodesic.cqrs.api.Command;

@ContextConfiguration(locations={"classpath:/spring/test-application-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CommandSchedulerTest {

	@Autowired
	private CommandScheduler scheduler;
	@Autowired
	CommandBus commandBus;
	@Autowired
	private EventBus eventBus;
	
	private CommandHandlerStub<SkelId> commandHandler;
	
	@Before
	public void setup() {
		commandHandler = new CommandHandlerStub<SkelId>(SkelId.class, commandBus, eventBus);
		commandBus.register(SkelId.class, commandHandler);
	}
	@Test
	public void test() {
		AddSkelCommand command = new AddSkelCommand(new SkelId("anId"));
		scheduler.schedule(command, Period.ZERO);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Collection<Command<SkelId>> commands = commandHandler.commands();
		assertEquals(1, commands.size());
		assertEquals(command, commands.iterator().next());
	}
}

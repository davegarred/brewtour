package org.garred.skeleton.infrastructure;

import java.io.IOException;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nullgeodesic.cqrs.CommandBus;
import com.nullgeodesic.cqrs.api.Command;

public class CommandScheduler {

	private final Scheduler scheduler;
	private ObjectMapper serializer;
	
	public CommandScheduler(CommandBus commandBus, Scheduler scheduler, ObjectMapper serializer) {
		this.serializer = serializer;
		this.scheduler = scheduler;
	}
	
	public void schedule(Command<?> command, Period period) {
		ZonedDateTime time = ZonedDateTime.now().plus(period);
		schedule(command,time);
	}
	public void schedule(Command<?> command, ZonedDateTime time) {
		try {
			JobDetail jobDetail = jobDetail(command);
			Trigger trigger = trigger(time);
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException | JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	private JobDetail jobDetail(Command<?> command) throws JsonProcessingException {
		JobBuilder builder = JobBuilder.newJob(FireCommandJob.class);
		builder.usingJobData("command", command.getClass().getCanonicalName());
		builder.usingJobData("payload", serializer.writeValueAsString(command));
		return builder.build();
	}

	private Trigger trigger(ZonedDateTime time) {
		TriggerBuilder<Trigger> builder = TriggerBuilder.newTrigger();
		Date startDate = Date.from(time.toInstant());
		builder.startAt(startDate);
		return builder.build();
	}
	
	public static class FireCommandJob implements Job {
		@Override
		public void execute(JobExecutionContext context) throws JobExecutionException {
			ApplicationContext  appContext = null;
			try {
				SchedulerContext schedContext = context.getScheduler().getContext();
				appContext = (ApplicationContext) schedContext.get("applicationContext");
			} catch (SchedulerException e) {
				throw new RuntimeException(e);
			}
			if(appContext == null) {
				throw new RuntimeException("Spring application context could not be found from the Quartz JobExecutionContext");
			}
			ObjectMapper serializer = (ObjectMapper) appContext.getBean("serializer");
			CommandBus commandBus = (CommandBus) appContext.getBean("commandBus");
			try {
				JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
				String commandCanonicalName = (String) jobDataMap.get("command");
				String payload = (String) jobDataMap.get("payload");
				@SuppressWarnings("unchecked")
				Class<Command<?>> clazz = (Class<Command<?>>) Class.forName(commandCanonicalName.toString());
				Command<?> command = serializer.readValue(payload.toString(), clazz);
				commandBus.send(command);
			} catch (ClassNotFoundException | IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}

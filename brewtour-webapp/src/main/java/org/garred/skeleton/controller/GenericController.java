package org.garred.skeleton.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.Reader;

import org.garred.skeleton.api.SkelId;
import org.garred.skeleton.api.command.AddSkelCommand;
import org.garred.skeleton.api.command.AddValueSkelCommand;
import org.garred.skeleton.infrastructure.SkeletonRepositoryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.nullgeodesic.cqrs.CommandGateway;
import com.nullgeodesic.cqrs.logging.MessageLogger;

@Controller
public class GenericController extends AbstractRestController {
	
	private static final SkelId TEST_ID = new SkelId("TEST_ID");
	
	private final ObjectMapper objectMapper;
	private final SkeletonRepositoryImpl skeletonRepo;
	private final CommandGateway commandGateway;
	private final MessageLogger commandLogger;
	private final MessageLogger eventLogger;
	
	
	public GenericController(SkeletonRepositoryImpl skeletonRepo, CommandGateway commandGateway, MessageLogger commandLogger, MessageLogger eventLogger) {
		this.skeletonRepo = skeletonRepo;
		this.commandGateway = commandGateway;
		this.commandLogger = commandLogger;
		this.eventLogger = eventLogger;
		this.objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JSR310Module());
	}

	@RequestMapping(value = "/commands", method = GET, produces="application/json")
	@ResponseBody
	public String commands() throws JsonProcessingException {
		return objectMapper.writeValueAsString(commandLogger.replay());
	}
	
	@RequestMapping(value = "/events", method = GET, produces="application/json")
	@ResponseBody
	public String events() throws JsonProcessingException {
		return objectMapper.writeValueAsString(eventLogger.replay());
	}

	@RequestMapping(value = "/generic", method = GET, produces="application/json")
	@ResponseBody
	public String get() throws JsonProcessingException {
		return objectMapper.writeValueAsString(skeletonRepo.get(TEST_ID));
	}
	
	@RequestMapping(value = "/add", method = POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public String add(Reader data) throws Exception {
		if(skeletonRepo.get(TEST_ID) == null) {
			commandGateway.fire(new AddSkelCommand(TEST_ID));
		}
		UpdatedValue newValue = objectMapper.readValue(data, UpdatedValue.class);
		commandGateway.fire(new AddValueSkelCommand(TEST_ID, newValue.newValue));
		return get();
	}
	
	public static class UpdatedValue {
		public String newValue;
	}
}

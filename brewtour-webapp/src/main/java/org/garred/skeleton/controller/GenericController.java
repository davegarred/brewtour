package org.garred.skeleton.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.garred.skeleton.api.SkelId;
import org.garred.skeleton.infrastructure.SkeletonRepositoryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

@Controller
public class GenericController extends AbstractRestController {

	private static final SkelId TEST_ID = new SkelId("TEST_ID");

	private final ObjectMapper objectMapper;
	private final SkeletonRepositoryImpl skeletonRepo;


	public GenericController(SkeletonRepositoryImpl skeletonRepo) {
		this.skeletonRepo = skeletonRepo;
		this.objectMapper = new ObjectMapper();
		this.objectMapper.registerModule(new JSR310Module());
	}

	@RequestMapping(value = "/commands", method = GET, produces="application/json")
	@ResponseBody
	public String commands() throws JsonProcessingException {
		return null;
	}

	@RequestMapping(value = "/events", method = GET, produces="application/json")
	@ResponseBody
	public String events() throws JsonProcessingException {
		return null;
	}

}

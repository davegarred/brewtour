package org.garred.skeleton.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ApplicationStatusController extends AbstractRestController implements ApplicationContextAware {
	
	private final ObjectMapper objectMapper;
	private ApplicationContext applicationContext;
	
	public ApplicationStatusController() {
		this.objectMapper = new ObjectMapper();
	}

	@RequestMapping(value = "/healthcheck", method = GET, produces="application/json")
	@ResponseBody
	public String healthCheck() throws JsonProcessingException {
		return objectMapper.writeValueAsString(new ApplicationStatus(applicationContext));
	}
	
	public static class ApplicationStatus {

		public final String[] activeProfiles;
		public final String[] defaultProfiles;
		public final Map<String, Object> systemProperties;
		public final Map<String, Object> environmentDetails;
		public final String context;
		public final ZonedDateTime startTime;
		
		public final Map<String,String> applicationBeans = new HashMap<>();
		
		public ApplicationStatus(ApplicationContext applicationContext) {
			context = applicationContext.getApplicationName();
			startTime = new Date(applicationContext.getStartupDate()).toInstant().atZone(ZoneId.systemDefault());
			
			Environment env = applicationContext.getEnvironment();
			this.activeProfiles = env.getActiveProfiles();
			this.defaultProfiles = env.getDefaultProfiles();
			this.systemProperties = ((AbstractEnvironment)env).getSystemProperties();
			this.environmentDetails = ((AbstractEnvironment)env).getSystemEnvironment();
			for(String bean : applicationContext.getParent().getBeanDefinitionNames()) {
				this.applicationBeans.put(bean, applicationContext.getParent().getBean(bean).getClass().getSimpleName());
			}
		}
		
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
}

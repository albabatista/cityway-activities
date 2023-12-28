package com.cityway.activities.config;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class LogAspectConfig {
	
	private static final Logger LOG = LoggerFactory.getLogger(LogAspectConfig.class);
	
	@Before( value= "execution( * com.cityway.activities.presentation.restcontrollers.*.*(..))" )
	public void controllersLogger(JoinPoint joinPoint) {
		String controllerName = joinPoint.getTarget().getClass().getSimpleName();
		String method = joinPoint.getSignature().getName().toUpperCase();
		String params = Arrays.toString(joinPoint.getArgs());
		
		LOG.info("{} receive {} request with params: {}" ,controllerName, method, params);
	}

}

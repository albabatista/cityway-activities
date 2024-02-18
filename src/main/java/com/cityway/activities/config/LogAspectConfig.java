package com.cityway.activities.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;


/**
 * The Class LogAspectConfig.
 */
@Configuration
@Aspect
@Slf4j
public class LogAspectConfig {


	/**
	 * Controllers logger.
	 * 
	 * @param joinPoint the join point
	 */
	@Before(value = "execution( * com.cityway.activities.presentation.restcontrollers.*.*(..))")
	public void controllersLogger(JoinPoint joinPoint) {
		String controllerName = joinPoint.getTarget().getClass().getSimpleName();
		String method = joinPoint.getSignature().getName().toUpperCase();
		String params = extractParams(joinPoint.getArgs());

		if (!params.isBlank()) {
			log.info("{} call {} method with params: {}", controllerName, method, params);

		} else {
			log.info("{} call {} method", controllerName, method);
		}

	}

	/**
	 * Extract params.
	 *
	 * @param array the array
	 * @return the string
	 */
	private String extractParams(Object[] array) {
		List<String> list = new ArrayList<>();

		for (int i = 0; i < array.length; i++) {

			boolean isPriceZero = array[i] instanceof Double && (double) array[i] == 0;

			if (array[i] != null && !isPriceZero) {
				list.add(array[i].toString());
			}

		}
	
		return list.isEmpty()? "" : Arrays.toString(list.toArray());
	}

}

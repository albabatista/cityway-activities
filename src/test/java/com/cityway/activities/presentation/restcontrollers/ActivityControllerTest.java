package com.cityway.activities.presentation.restcontrollers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.cityway.activities.business.models.Activity;
import com.cityway.activities.business.services.ActivityService;
import com.cityway.activities.integration.mappers.ActivityMapper;
import com.cityway.activities.integration.mappers.CategoryMapper;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ActivityController.class)
class ActivityControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ActivityService activityService;
	
	private Activity activity;
	private List<Activity> activities;

	@BeforeEach
	void setUp() throws Exception {
		activity = objectMapper.readValue(new URL("file:src/test/resources/Activity.json"), Activity.class);
		activities = objectMapper.readValue(new URL("file:src/test/resources/List Activities.json"), new TypeReference<List<Activity>>(){});
	}

	@Test
	void getAllTest() throws Exception {
		when(activityService.getAll()).thenReturn(activities);
		
		MvcResult mvcResult = mockMvc.perform(get("/activities").contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(6)))
				.andReturn();
	
		String responseBody= mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
	
		assertThat(responseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(activities));
	}
	
	
}

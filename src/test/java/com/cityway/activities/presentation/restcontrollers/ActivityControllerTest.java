package com.cityway.activities.presentation.restcontrollers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.cityway.activities.business.models.Activity;
import com.cityway.activities.business.models.Category;
import com.cityway.activities.business.services.ActivityService;
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
		activity = objectMapper.readValue(getJsonUrlFromTestResources("Activity.json"), Activity.class);
		activities = initList(null);
	}
	
	@Test
	void createTest() throws Exception {
		
		String requestBody = objectMapper.writeValueAsString(activity);
		
		mockMvc.perform(post("/activities")
					.content(requestBody)
					.contentType("application/json"))
					.andExpect(status().isCreated())
					.andExpect(header().string("Location", "http://localhost/activities/"+activity.getId()));	
	}
	
	@ParameterizedTest
	@ValueSource(strings = "658da42b7e5c3c47845cbfe8")
	void getByIdTest(String id) throws Exception {
		
		when(activityService.read(id)).thenReturn(activity);
		
		MvcResult mvcResult = mockMvc.perform(get("/activities/"+id).contentType("application/json"))
				.andExpect(status().isOk())
				.andReturn();

		String responseBody= mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertThat(responseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(activity));
	}

	@ParameterizedTest
	@EmptySource
	@ValueSource(strings = { "Paris", "German", "Saint", "ENTRANCE_TICKETS" })
	void getTest(String paramValue) throws Exception {
		MvcResult mvcResult = null;

		if (paramValue.isEmpty()) { // GetAll
			when(activityService.getAll()).thenReturn(activities);
			mvcResult = getMvcResultByParam(null, paramValue);

		} else if ("Paris".equals(paramValue)) { // GetByCity
			activities = initList("List Activities By City.json");
			when(activityService.getByCity(paramValue)).thenReturn(activities);
			mvcResult = getMvcResultByParam("city", paramValue);

		} else if ("ENTRANCE_TICKETS".equals(paramValue)) { // GetByCategory
			activities = initList("List Activities By Category.json");
			when(activityService.getByCategory(Category.ENTRANCE_TICKETS)).thenReturn(activities);
			mvcResult = getMvcResultByParam("category", paramValue);

		} else if ("Saint".equals(paramValue)) { // GetByName
			activities = List.of(activity);
			when(activityService.getByNameContaining("Saint")).thenReturn(activities);
			mvcResult = getMvcResultByParam("name", paramValue);

		} else if ("German".equals(paramValue)) { // GetByLanguage
			activities = initList("List Activities By Language.json");
			when(activityService.getByLanguaguesContaining("German")).thenReturn(activities);
			mvcResult = getMvcResultByParam("language", paramValue);
		}

		String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertThat(responseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(activities));

	}

	// *****************************************************************
	//
	// PRIVATE METHODS
	//
	// *****************************************************************

	/**
	 * Gets the json url from test resources.
	 *
	 * @param fileName the file name
	 * @return the json url from test resources
	 * @throws MalformedURLException the malformed URL exception
	 */
	private URL getJsonUrlFromTestResources(String fileName) throws MalformedURLException {
		final String testResourcesPath = "file:src/test/resources/";

		if (fileName != null && !fileName.isBlank()) {
			return new URL(testResourcesPath + fileName);
		}
		return new URL(testResourcesPath + "List Activities.json");

	}

	/**
	 * Inits the list.
	 *
	 * @param jsonFileName the json file name
	 * @return the list
	 * @throws StreamReadException   the stream read exception
	 * @throws DatabindException     the databind exception
	 * @throws MalformedURLException the malformed URL exception
	 * @throws IOException           Signals that an I/O exception has occurred.
	 */
	private List<Activity> initList(String jsonFileName)
			throws StreamReadException, DatabindException, MalformedURLException, IOException {
		return objectMapper.readValue(getJsonUrlFromTestResources(jsonFileName), new TypeReference<List<Activity>>() {
		});
	}

	/**
	 * Gets the mvc result by param.
	 *
	 * @param paramName  the param name
	 * @param paramValue the param value
	 * @return the mvc result by param
	 * @throws Exception the exception
	 */
	private MvcResult getMvcResultByParam(String paramName, String paramValue) throws Exception {

		if (paramName != null && !paramName.isBlank()) {
			return mockMvc.perform(get("/activities").param(paramName, paramValue).contentType("application/json"))
					.andExpect(status().isOk()).andReturn();

		}
		return mockMvc.perform(get("/activities").contentType("application/json")).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(6))).andReturn();

	}
}

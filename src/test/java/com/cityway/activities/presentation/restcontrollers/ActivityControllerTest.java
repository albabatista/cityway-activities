package com.cityway.activities.presentation.restcontrollers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.MalformedURLException;
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
	void getAllTest() throws Exception {
		when(activityService.getAll()).thenReturn(activities);
		
		MvcResult mvcResult = mockMvc.perform(get("/activities").contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(6)))
				.andReturn();
	
		String responseBody= mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
	
		assertThat(responseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(activities));
	}

	@Test
	void getByCityTest() throws Exception {
		activities = initList("List Activities By City.json");
		
		when(activityService.getByCity("Paris")).thenReturn(activities);
		
		MvcResult mvcResult = mockMvc.perform(get("/activities").param("city", "Paris").contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(2)))
				.andReturn();
	
		String responseBody= mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
	
		assertThat(responseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(activities));
	}
	
	@Test
	void getByCategory() throws Exception {
		activities = initList("List Activities By Category.json");
		
		when(activityService.getByCategory(Category.ENTRANCE_TICKETS)).thenReturn(activities);
		
		MvcResult mvcResult = mockMvc.perform(get("/activities").param("category", "ENTRANCE_TICKETS").contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(2)))
				.andReturn();
	
		String responseBody= mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
	
		assertThat(responseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(activities));
	}
	
	@Test
	void getByName() throws Exception {
		activities = List.of(activity);
		
		when(activityService.getByNameContaining("Saint")).thenReturn(activities);
		
		MvcResult mvcResult = mockMvc.perform(get("/activities").param("name", "Saint").contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(1)))
				.andReturn();
	
		String responseBody= mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
	
		assertThat(responseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(activities));
	}

	@Test
	void getByLanguage() throws Exception {
		activities = initList("List Activities By Language.json");
		
		when(activityService.getByLanguaguesContaining("German")).thenReturn(activities);
		
		MvcResult mvcResult = mockMvc.perform(get("/activities").param("language", "German").contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(2)))
				.andReturn();
	
		String responseBody= mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
	
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

		} else {
			return new URL(testResourcesPath + "List Activities.json");
		}
	}
	
	
	/**
	 * Inits the list.
	 *
	 * @param jsonFileName the json file name
	 * @return the list
	 * @throws StreamReadException the stream read exception
	 * @throws DatabindException the databind exception
	 * @throws MalformedURLException the malformed URL exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private List<Activity> initList(String jsonFileName) throws StreamReadException, DatabindException, MalformedURLException, IOException {
		return objectMapper.readValue(getJsonUrlFromTestResources(jsonFileName), new TypeReference<List<Activity>>() {});
	}
}

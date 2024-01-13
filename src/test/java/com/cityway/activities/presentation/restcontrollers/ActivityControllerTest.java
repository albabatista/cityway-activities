package com.cityway.activities.presentation.restcontrollers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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

	private final String CONTENT_TYPE = "application/json";
	private final String ENDPOINT = "/activities";
	private final String ENDPOINT_PATH_VARIABLE = "/activities/";

	@BeforeEach
	void setUp() throws Exception {
		activity = objectMapper.readValue(getJsonUrlFromTestResources("Activity.json"), Activity.class);
		activities = initList(null);
	}

	@Test
	void createTest() throws Exception {
		String requestBody = objectMapper.writeValueAsString(activity);

		mockMvc.perform(post(ENDPOINT).content(requestBody).contentType(CONTENT_TYPE)).andExpect(status().isCreated())
				.andExpect(header().string("Location", "http://localhost/activities/" + activity.getId()));
	}

	@Test
	void deleteTest() throws Exception {
		String requestBody = objectMapper.writeValueAsString(activity);

		when(activityService.read(activity.getId())).thenReturn(activity);

		mockMvc.perform(delete(ENDPOINT).content(requestBody).contentType(CONTENT_TYPE))
				.andExpect(status().isNoContent()).andReturn();
	}

	@ParameterizedTest
	@EmptySource
	@ValueSource(strings = { "658da42b7e5c3c47845cbfe8" })
	void deleteById(String id) throws Exception {
		if (!id.isEmpty()) {
			when(activityService.read(id)).thenReturn(activity);
			mockMvc.perform(delete(ENDPOINT_PATH_VARIABLE + id).contentType(CONTENT_TYPE))
					.andExpect(status().isNoContent()).andReturn();

		} else {
			mockMvc.perform(delete(ENDPOINT_PATH_VARIABLE + id).contentType(CONTENT_TYPE))
					.andExpect(status().isNotFound()).andReturn();
		}
	}

	@Test
	void updateTest() throws Exception {
		String requestBody = objectMapper.writeValueAsString(activity);

		when(activityService.read(activity.getId())).thenReturn(activity);

		mockMvc.perform(put(ENDPOINT).content(requestBody).contentType(CONTENT_TYPE)).andExpect(status().isOk());
	}

	@ParameterizedTest
	@EmptySource
	@ValueSource(strings = { "658da42b7e5c3c47845cbfe8" })
	void getByIdTest(String id) throws Exception {

		if (!id.isEmpty()) {
			when(activityService.read(id)).thenReturn(activity);

			MvcResult mvcResult = mockMvc.perform(get(ENDPOINT_PATH_VARIABLE + id).contentType(CONTENT_TYPE))
					.andExpect(status().isOk()).andReturn();

			String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
			assertThat(responseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(activity));

		} else {
			when(activityService.read(id)).thenReturn(null);
			mockMvc.perform(get(ENDPOINT_PATH_VARIABLE + id).contentType(CONTENT_TYPE)).andExpect(status().isNotFound())
					.andReturn();
		}

	}

	@Test
	void getAllTest() throws Exception {
		when(activityService.getAll()).thenReturn(activities);
		MvcResult mvcResult  = getMvcResultByParam(null, "");
		
		String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertThat(responseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(activities));
	}

	@Test
	void getByCityTest() throws Exception {
		activities = initList("List Activities By City.json");
		when(activityService.getByCity("Paris")).thenReturn(activities);
		MvcResult mvcResult = getMvcResultByParam("city", "Paris");

		String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertThat(responseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(activities));
	}

	@Test
	void getByCategoryTest() throws Exception {
		activities = initList("List Activities By Category.json");
		when(activityService.getByCategory(Category.ENTRANCE_TICKETS)).thenReturn(activities);
		MvcResult mvcResult = getMvcResultByParam("category", "ENTRANCE_TICKETS");

		String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertThat(responseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(activities));
	}

	@Test
	void getByNameTest() throws Exception {
		activities = List.of(activity);
		when(activityService.getByNameContaining("Saint")).thenReturn(activities);
		MvcResult mvcResult = getMvcResultByParam("name", "Saint");

		String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertThat(responseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(activities));
	}

	@Test
	void getByLanguageTest() throws Exception {
		activities = initList("List Activities By Language.json");
		when(activityService.getByLanguaguesContaining("German")).thenReturn(activities);
		MvcResult mvcResult = getMvcResultByParam("language", "German");

		String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertThat(responseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(activities));
	}

	@Test
	void getByDateTest() throws Exception {
		activities = List.of(activity);
		when(activityService.getByDateAvailable("11/12/2023")).thenReturn(activities);
		MvcResult mvcResult = getMvcResultByParam("date", "11/12/2023");

		String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertThat(responseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(activities));
	}

	@Test
	void getByPriceTest() throws Exception {
		activities = List.of(activity);
		when(activityService.getByPriceBetween(150, 160)).thenReturn(activities);
		MvcResult mvcResult = mockMvc
				.perform(get(ENDPOINT).param("min", "150").param("max", "160").contentType(CONTENT_TYPE))
				.andExpect(status().isOk()).andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertThat(responseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(activities));
	}

	@Test
	void getByAdminPetsTest() throws Exception {
		activities = new ArrayList<Activity>();
		when(activityService.getByAdminPetsTrue()).thenReturn(activities);

		mockMvc.perform(get(ENDPOINT).param("adminPets", "true").contentType(CONTENT_TYPE))
				.andExpect(status().isNotFound()).andReturn();
	}

	@Test
	void getByWheelchairAccessibleTest() throws Exception {
		activities = new ArrayList<Activity>();
		when(activityService.getByWheelchairAccessibleTrue()).thenReturn(activities);

		mockMvc.perform(get(ENDPOINT).param("wheelchairAccessible", "true").contentType(CONTENT_TYPE))
				.andExpect(status().isNotFound()).andReturn();
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
			return mockMvc.perform(get(ENDPOINT).param(paramName, paramValue).contentType(CONTENT_TYPE))
					.andExpect(status().isOk()).andReturn();

		}
		return mockMvc.perform(get(ENDPOINT).contentType(CONTENT_TYPE)).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(6))).andReturn();

	}
}

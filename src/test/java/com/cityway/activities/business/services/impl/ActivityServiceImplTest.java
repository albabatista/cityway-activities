package com.cityway.activities.business.services.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cityway.activities.business.models.Activity;
import com.cityway.activities.integration.mappers.ActivityMapper;
import com.cityway.activities.integration.models.ActivityDto;
import com.cityway.activities.integration.repositories.ActivityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class ActivityServiceImplTest {

	@Mock
	private ActivityRepository activityRepository;

	@Mock
	private ActivityMapper activityMapper;

	@InjectMocks
	private ActivityServiceImpl activityServiceImpl;

	private Activity activity;

	private ActivityDto activityDto;

	@BeforeEach
	void setUp() throws Exception {
		URL pathJsonFile = new URL("file:src/test/resources/Activity.json");
		ObjectMapper objectMapper = new ObjectMapper();

		activity = objectMapper.readValue(pathJsonFile, Activity.class);
		activity.setId("6571");
		activityDto = activityMapper.activityToDto(activity);
	}

	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	void createTest(boolean activityIsNull) {

		activity = (activityIsNull) ? null : activity;

		if (activityIsNull) {
			assertThrows(IllegalArgumentException.class, () -> activityServiceImpl.create(activity));

		} else {
			activityServiceImpl.create(activity);
			verify(activityRepository, times(1)).save(activityDto);
		}

	}


}

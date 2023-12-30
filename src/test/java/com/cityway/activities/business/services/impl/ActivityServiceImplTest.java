package com.cityway.activities.business.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cityway.activities.business.models.Activity;
import com.cityway.activities.integration.mappers.ActivityMapper;
import com.cityway.activities.integration.models.ActivityDto;
import com.cityway.activities.integration.repositories.ActivityRepository;
import com.cityway.activities.presentation.exceptions.ActivityNotFoundException;
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
		activityDto = objectMapper.readValue(pathJsonFile, ActivityDto.class);
	}

	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	void createTest(boolean activityIsNull) {

		activity = (activityIsNull) ? null : activity;

		if (activityIsNull) {
			assertThrows(IllegalArgumentException.class, () -> activityServiceImpl.create(activity));

		} else {
			when(activityMapper.activityToDto(activity)).thenReturn(activityDto);
			activityServiceImpl.create(activity);
			verify(activityRepository, times(1)).save(activityDto);
		}

	}

	@ParameterizedTest
	@EmptySource
	@ValueSource(strings = "658da42b7e5c3c47845cbfe8")
	void readTest(String id) {
		when(activityMapper.activityToDto(activity)).thenReturn(activityDto);
		activityServiceImpl.create(activity);
		
		if (!id.isEmpty()) {
			when(activityRepository.findById(id)).thenReturn(Optional.of(activityDto));
			when(activityMapper.dtoToActivity(activityDto)).thenReturn(activity);
			assertEquals(activity, activityServiceImpl.read(id));
		}else {
			when(activityRepository.findById(id)).thenReturn(Optional.empty());
			assertNull(activityServiceImpl.read(id));
		}

	}
	
	@ParameterizedTest
	@NullSource
	@EmptySource
	@ValueSource(strings = "658da42b7e5c3c47845cbfe8")
	void updateTest(String id) {
		if (id == null) {
			assertThrows(IllegalArgumentException.class, () -> activityServiceImpl.update(null));
			
		}else if (id.isEmpty()) {
			activity = new Activity();
			activity.setId(id);
			assertThrows(ActivityNotFoundException.class, () -> activityServiceImpl.update(activity));
		}else {
			when(activityRepository.existsById(id)).thenReturn(true);
			when(activityMapper.activityToDto(activity)).thenReturn(activityDto);
			activityServiceImpl.update(activity);
			verify(activityRepository, times(1)).save(activityDto);
		}
		

	}

}

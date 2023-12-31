package com.cityway.activities.business.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cityway.activities.business.models.Activity;
import com.cityway.activities.business.models.Category;
import com.cityway.activities.integration.mappers.ActivityMapper;
import com.cityway.activities.integration.mappers.CategoryMapper;
import com.cityway.activities.integration.models.ActivityDto;
import com.cityway.activities.integration.models.CategoryDto;
import com.cityway.activities.integration.repositories.ActivityRepository;
import com.cityway.activities.presentation.exceptions.ActivityNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class ActivityServiceImplTest {

	@Mock
	private ActivityRepository activityRepository;

	@Mock
	private ActivityMapper activityMapper;

	@Mock
	private CategoryMapper categoryMapper;

	@InjectMocks
	private ActivityServiceImpl activityServiceImpl;

	private Activity activity;

	private ActivityDto activityDto;
	private ActivityDto activity2Dto;
	private ActivityDto activity3Dto;

	private CategoryDto categoryDto;

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

		} else if (id.isEmpty()) {
			activity = new Activity();
			activity.setId(id);
			assertThrows(ActivityNotFoundException.class, () -> activityServiceImpl.update(activity));
		} else {
			when(activityRepository.existsById(id)).thenReturn(true);
			when(activityMapper.activityToDto(activity)).thenReturn(activityDto);
			activityServiceImpl.update(activity);
			verify(activityRepository, times(1)).save(activityDto);
		}

	}

	@ParameterizedTest
	@EmptySource
	@ValueSource(strings = "658da42b7e5c3c47845cbfe8")
	void deleteTest(String id) {
		when(activityMapper.activityToDto(activity)).thenReturn(activityDto);
		activityServiceImpl.create(activity);
		
		if (!id.isEmpty()) {
			activityServiceImpl.delete(id);
			assertNull(activityServiceImpl.read(id));
		
		}else {
			activityServiceImpl.delete(activity);
			assertNull(activityServiceImpl.read(activity.getId()));
		}
		
	}

	@Test
	void getByCategory() {
		initGetTest();
		when(categoryMapper.categoryToDto(Category.DAY_TRIP)).thenReturn(categoryDto);

		when(activityRepository.findByCategory(categoryDto)).thenReturn(List.of(activityDto));
		assertEquals(1, activityServiceImpl.getByCategory(Category.DAY_TRIP).size());
	}

	@Test
	void getAllTest() {
		initGetTest();
		when(activityRepository.findAll()).thenReturn(List.of(activityDto, activity2Dto, activity3Dto));
		assertEquals(3, activityServiceImpl.getAll().size());
	}

	@Test
	void getByCityTest() {
		initGetTest();
		when(activityRepository.findByCity("Paris")).thenReturn(List.of(activityDto, activity2Dto));
		assertEquals(2, activityServiceImpl.getByCity("Paris").size());
	}

	@Test
	void getByNameTest() {
		initGetTest();
		when(activityRepository.findByNameContaining("Saint")).thenReturn(List.of(activityDto));
		assertEquals(1, activityServiceImpl.getByNameContaining("Saint").size());
	}

	@Test
	void getByDateTest() {
		initGetTest();
		when(activityRepository.findByDate("11/12/2023")).thenReturn(List.of(activityDto, activity2Dto));
		assertEquals(2, activityServiceImpl.getByDateAvailable("11/12/2023").size());
	}

	@Test
	void getByLanguageTest() {
		initGetTest();
		when(activityRepository.findByLanguagesContaining("English"))
				.thenReturn(List.of(activityDto, activity2Dto, activity3Dto));
		assertEquals(3, activityServiceImpl.getByLanguaguesContaining("English").size());
	}

	@Test
	void getByPriceBetweenTest() {
		initGetTest();
		when(activityRepository.findByPriceBetween(30, 50)).thenReturn(List.of(activity2Dto, activity3Dto));
		assertEquals(2, activityServiceImpl.getByPriceBetween(30, 50).size());
	}

	@Test
	void getByAdminPetsTrueTest(){
		when(activityRepository.findByAdminPetsTrue()).thenReturn(List.of());
		assertEquals(0, activityServiceImpl.getByAdminPetsTrue().size());
	}

	@Test
	void getByWheelchairAccessibleTrueTest(){
		when(activityRepository.findByWheelchairAccessibleTrue()).thenReturn(List.of());
		assertEquals(0, activityServiceImpl.getByWheelchairAccessibleTrue().size());
	}

	// ***************************************************************
	//
	// PRIVATE METHODS
	//
	// ***************************************************************

	private void initGetTest() {

		Activity activity2 = new Activity();
		activity2.setName("Eiffel Tower Climb");
		activity2.setCategory(Category.ENTRANCE_TICKETS);
		activity2.setLanguages(Set.of("English", "Spanish"));
		activity2.setCity("Paris");
		activity2.setPrice(39);

		Map<String, Set<String>> datesAvailables = new HashMap<String, Set<String>>();
		datesAvailables.put("11/12/2023", Set.of("11:30", "17:30"));
		activity2.setDatesAvailables(Set.of(datesAvailables));

		activity2Dto = new ActivityDto();
		activity2Dto.setName("Eiffel Tower Climb");
		activity2Dto.setCategory(CategoryDto.ENTRANCE_TICKETS);
		activity2Dto.setLanguages(Set.of("English", "Spanish"));
		activity2Dto.setCity("Paris");
		activity2Dto.setPrice(39);
		activity2Dto.setDatesAvailables(Set.of(datesAvailables));

		Activity activity3 = new Activity();
		activity3.setName("Colosseum, Roman Forum & Palatine Hill Tour");
		activity3.setCategory(Category.GUIDED_TOUR);
		activity3.setLanguages(Set.of("English", "Spanish"));
		activity3.setCity("Rome");
		activity3.setPrice(47);

		activity3Dto = new ActivityDto();
		activity3Dto.setName("Colosseum, Roman Forum & Palatine Hill Tour");
		activity3Dto.setCategory(CategoryDto.GUIDED_TOUR);
		activity3Dto.setLanguages(Set.of("English", "Spanish"));
		activity3Dto.setCity("Rome");
		activity3Dto.setPrice(47);

		when(activityMapper.activityToDto(activity)).thenReturn(activityDto);
		when(activityMapper.activityToDto(activity2)).thenReturn(activity2Dto);
		when(activityMapper.activityToDto(activity3)).thenReturn(activity3Dto);

		activityServiceImpl.create(activity);
		activityServiceImpl.create(activity2);
		activityServiceImpl.create(activity3);

	}

}

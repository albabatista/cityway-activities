package com.cityway.activities.business.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ActivitiesUtilsTest {

	@ParameterizedTest
	@EmptySource
	@NullSource
	@ValueSource(strings = { "  ", " mont saint michel day trip. ", " MONT SAINT MICHEL DAY TRIP" })
	void capitalizeTest(String phrase) {

		String resultExpected = (phrase == null || phrase.isBlank()) ? ActivitiesUtils.EMPTY
				: "Mont Saint Michel Day Trip";
		assertEquals(resultExpected, ActivitiesUtils.capitalize(phrase));

	}

}

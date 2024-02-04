package com.cityway.activities.integration.annotations.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CapitalizeConverterTest {

	@InjectMocks
	CapitalizeConverter capitalizeConverter;

	@ParameterizedTest
	@EmptySource
	@NullSource
	@ValueSource(strings = { "  ", " mont saint michel day trip ", " MONT SAINT MICHEL DAY TRIP" })
	void test(String phrase) {
		String resultExpected = (phrase == null || phrase.isBlank()) ? "" : "Mont Saint Michel Day Trip";
		assertEquals(resultExpected, capitalizeConverter.convert(phrase));

	}

}

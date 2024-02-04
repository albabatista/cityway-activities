package com.cityway.activities.integration.annotations.impl;

import org.apache.commons.lang.WordUtils;

import com.fasterxml.jackson.databind.util.StdConverter;

/**
 * The Class CapitalizeConverter.
 */
public class CapitalizeConverter extends StdConverter<String, String> {

	private final String EMPTY = "";

	/**
	 * Convert.
	 *
	 * @param value the value
	 * @return the string
	 */
	@Override
	public String convert(String value) {
		if (value != null) {
			return WordUtils.capitalizeFully(value).trim();
		}
		return EMPTY;
	}

}

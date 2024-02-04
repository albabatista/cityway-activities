package com.cityway.activities.business.utils;

import org.apache.commons.lang.WordUtils;

public class ActivityUtils {

	public static String toCapitalizeFully(String value) {
		return (value != null) ? WordUtils.capitalizeFully(value).trim() : null;
	}

}

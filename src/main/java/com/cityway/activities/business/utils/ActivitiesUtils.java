package com.cityway.activities.business.utils;

import java.util.function.UnaryOperator;


/**
 * The Class ActivitiesUtils.
 */
public class ActivitiesUtils {

	public static String EMPTY = "";

	public static String capitalize(String word) {
		UnaryOperator<String> capitalizeWord = t -> t.substring(0, 1).toUpperCase() + t.substring(1);
		return (word.isBlank())? EMPTY : capitalizeWord.apply(word).trim();
 	
	}

}

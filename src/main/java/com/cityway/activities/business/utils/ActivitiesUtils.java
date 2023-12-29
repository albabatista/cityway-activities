package com.cityway.activities.business.utils;

import java.util.function.UnaryOperator;

/**
 * The Class ActivitiesUtils.
 */
public class ActivitiesUtils {

	public static String EMPTY = "";

	public static String SPACE = " ";

	/**
	 * Capitalize.
	 *
	 * @param phrase the phrase
	 * @return the string
	 */
	public static String capitalize(String phrase) {

		StringBuilder result = new StringBuilder();

		if (phrase !=null && !phrase.isBlank()) {
			
			String[] words = phrase.trim().split(" ");

			UnaryOperator<String> capitalizeWord = t -> t.substring(0, 1).toUpperCase() + t.substring(1);

			for (int i = 0; i < words.length; i++) {
				result.append(capitalizeWord.apply(words[i]));
				result.append(SPACE);
			}

		}

		return result.toString().trim();

	}

}

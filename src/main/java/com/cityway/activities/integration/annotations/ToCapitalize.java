package com.cityway.activities.integration.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

import com.cityway.activities.integration.annotations.impl.CapitalizeConverter;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * The Interface ToCapitalize.
 * 
 * Capitalize a string
 */
@Retention(RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(converter = CapitalizeConverter.class)
@JsonDeserialize(converter = CapitalizeConverter.class)
public @interface ToCapitalize {

}

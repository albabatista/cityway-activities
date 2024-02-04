package com.cityway.activities.integration.annotations.impl;

import org.springframework.data.convert.PropertyValueConverter;
import org.springframework.data.convert.ValueConversionContext;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.stereotype.Component;

import com.cityway.activities.business.utils.ActivityUtils;

@Component
public class ToCapitalizeFullyConverter
		implements PropertyValueConverter<String, String, ValueConversionContext<? extends PersistentProperty<?>>> {

	@Override
	public String read(String value, ValueConversionContext<? extends PersistentProperty<?>> context) {
		return ActivityUtils.toCapitalizeFully(value);
	}

	@Override
	public String write(String value, ValueConversionContext<? extends PersistentProperty<?>> context) {
		return ActivityUtils.toCapitalizeFully(value);
	}

}

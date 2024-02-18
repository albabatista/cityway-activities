package com.cityway.activities.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.stereotype.Component;

import com.cityway.activities.business.utils.ActivityUtils;
import com.cityway.activities.integration.models.ActivityDto;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MongoConfiguration {

	@Value("${spring.data.mongodb.database}")
	private String database;

	@Value("${spring.data.mongodb.uri}")
	private String uri;

	private List<Converter<?, ?>> converters = new ArrayList<>();

	@Bean
	MongoClient mongoClient() {
		return MongoClients.create(uri);
	}

	@Bean
	MongoOperations mongoTemplate(MongoClient mongoClient) {
		log.info("Loading Mongo Template...");

		MongoDatabaseFactory mongoDbFactory = new SimpleMongoClientDatabaseFactory(mongoClient, database);

		CustomConversions conversions = customConversions();

		MongoMappingContext mappingContext = new MongoMappingContext();
		DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);

		MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mappingContext);
		mongoConverter.setCustomConversions(conversions);

		// to set the properties up programmatically,
		mongoConverter.afterPropertiesSet();	
		log.info("Mongo Template loaded");

		return new MongoTemplate(mongoDbFactory, mongoConverter);
	}

	public MongoCustomConversions customConversions() {
		converters.add(new ToCapitalizeReadingConverter());
		converters.add(new ToCapitalizeWritingConverter());
		log.info("Converters {} registered", Arrays.asList(converters));
		return new MongoCustomConversions(converters);

	}

	private ActivityDto toCapitalizeActivityFields(ActivityDto activity) {
		activity.setCity(ActivityUtils.toCapitalizeFully(activity.getCity()));
		activity.setName(ActivityUtils.toCapitalizeFully(activity.getName()));
		activity.setLocation(ActivityUtils.toCapitalizeFully(activity.getLocation()));

		List <String> languages = activity.getLanguages().stream().map(ActivityUtils::toCapitalizeFully).toList();
		activity.setLanguages(Set.copyOf(languages));
		log.info("Result Activity: {}", activity);
		return activity;
	}

	@Component
	@ReadingConverter
	public class ToCapitalizeReadingConverter implements Converter<ActivityDto, ActivityDto> {
		@Override
		public ActivityDto convert(ActivityDto activity) {
			log.info("Converted Readed ActivityDto: {}", activity);
			return toCapitalizeActivityFields(activity);
		}

	}

	@Component
	@WritingConverter
	public class ToCapitalizeWritingConverter implements Converter<ActivityDto, ActivityDto> {

		@Override
		public ActivityDto convert(ActivityDto activity) {
			log.info("Converted Written ActivityDto: {}", activity);
			return toCapitalizeActivityFields(activity);
		}

	}

}

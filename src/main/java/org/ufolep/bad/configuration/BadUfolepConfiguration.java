package org.ufolep.bad.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BadUfolepConfiguration {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
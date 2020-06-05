package org.arhor.diploma.service.config;

import org.arhor.commons.util.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public Converter modelMapperConverter(final ModelMapper mapper) {
    return mapper::map;
  }
}

package org.arhor.diploma.util;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ModelMapperConverter implements Converter {

  private final ModelMapper mapper;

  @Override
  public <T, R> R convert(T item, Class<R> targetClass) {
    return mapper.map(item, targetClass);
  }
}

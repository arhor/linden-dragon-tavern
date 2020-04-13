package org.arhor.diploma.domain;

import lombok.Data;
import org.arhor.diploma.Identifiable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class AbstractEntity<T> implements Identifiable<T> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected T id;
}

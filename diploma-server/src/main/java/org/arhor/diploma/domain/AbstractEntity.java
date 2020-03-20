package org.arhor.diploma.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class AbstractEntity<T>
    implements DomainEntity<T> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected T id;
}

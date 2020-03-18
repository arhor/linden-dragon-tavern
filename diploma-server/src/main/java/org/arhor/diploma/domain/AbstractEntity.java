package org.arhor.diploma.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractEntity<T extends Serializable> implements DomainEntity<T> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected T id;

  @Override
  public T getId() {
    return id;
  }

  @Override
  public void setId(T id) {
    this.id = id;
  }
}

package org.arhor.diploma.data.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class DomainObject<T> implements Identifiable<T>, Deletable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected T id;

  @Column(nullable = false)
  private boolean deleted;
}

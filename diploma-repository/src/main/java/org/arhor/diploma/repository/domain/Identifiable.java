package org.arhor.diploma.repository.domain;

public interface Identifiable<ID> {

  ID getId();

  void setId(ID id);
}

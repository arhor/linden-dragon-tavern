package org.arhor.diploma.repository.domain;

public interface Deletable {

  boolean isDeleted();

  void setDeleted(boolean deleted);

  default void onDelete() {}
}

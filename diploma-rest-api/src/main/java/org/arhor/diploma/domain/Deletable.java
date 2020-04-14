package org.arhor.diploma.domain;

public interface Deletable {

  boolean isDeleted();

  void setDeleted(boolean deleted);

  default void onDelete() {}
}

package org.arhor.diploma.data.domain;

public interface Deletable {

  boolean isDeleted();

  void setDeleted(boolean deleted);

  default void onDelete() {}
}

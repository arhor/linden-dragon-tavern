package org.arhor.diploma;

public interface Deletable {

  boolean isDeleted();

  void setDeleted(boolean deleted);

  default void onDelete() {}
}

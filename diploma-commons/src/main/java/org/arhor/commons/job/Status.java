package org.arhor.commons.job;

public enum Status {

  NEW     (1),
  PENDING (2),
  DONE    (3),
  FAILED  (4);

  public final byte numericStatus;

  Status(final byte numericStatus) {
    this.numericStatus = numericStatus;
  }

  Status(final int numericStatus) {
    this((byte) numericStatus);
  }
}

package org.arhor.commons.job;

public interface Job<T> {

  Status getStatus();

  Result<T> getResult();

}

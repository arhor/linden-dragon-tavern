package org.arhor.diploma.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.arhor.commons.Deletable;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public abstract class DeletableAbstractEntity<T>
    extends AbstractEntity<T>
    implements Deletable {

  private boolean deleted;
}

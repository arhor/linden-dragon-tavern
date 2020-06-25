package org.arhor.diploma.domain.core

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
class CompositeId(var firstId: Long?, var secondId: Long?) : Serializable {

  private operator fun CompositeId?.component1(): Long? = this?.firstId

  private operator fun CompositeId?.component2(): Long? = this?.secondId

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is CompositeId) return false

    if (firstId != other.firstId) return false
    if (secondId != other.secondId) return false

    return true
  }

  override fun hashCode(): Int {
    var result = firstId?.hashCode() ?: 0
    result = 31 * result + (secondId?.hashCode() ?: 0)
    return result
  }

  override fun toString(): String {
    return "CompositeId(firstId=$firstId, secondId=$secondId)"
  }
}





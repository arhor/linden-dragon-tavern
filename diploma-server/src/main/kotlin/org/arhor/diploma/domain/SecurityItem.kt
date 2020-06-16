package org.arhor.diploma.domain;

import org.arhor.diploma.domain.core.DomainObject

class SecurityItem: DomainObject<Long>() {

  var securityProfileId: Long? = null

  var resourceId: Long? = null

  var isEdit: Boolean = false

  var isView: Boolean = false
}

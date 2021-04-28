package org.arhor.diploma

import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.data.persistence.domain.core.DeletableDomainObject

internal typealias TestDto = Identifiable<String>
internal typealias TestEntity = DeletableDomainObject<String>
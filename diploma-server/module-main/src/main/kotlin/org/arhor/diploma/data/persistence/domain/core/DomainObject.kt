package org.arhor.diploma.data.persistence.domain.core

import org.arhor.diploma.commons.Identifiable
import java.io.Serializable

/**
 * Base class for any entity used in the application.
 *
 * @param T primary key type
 */
abstract class DomainObject<T : Serializable> : Identifiable<T>, Serializable

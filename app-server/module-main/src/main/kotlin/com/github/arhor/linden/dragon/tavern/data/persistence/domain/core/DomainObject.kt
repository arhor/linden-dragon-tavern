package com.github.arhor.linden.dragon.tavern.data.persistence.domain.core

import com.github.arhor.linden.dragon.tavern.common.Identifiable
import java.io.Serializable

/**
 * Base class for any entity used in the application.
 *
 * @param T primary key type
 */
abstract class DomainObject<T : Serializable> : Identifiable<T>, Serializable

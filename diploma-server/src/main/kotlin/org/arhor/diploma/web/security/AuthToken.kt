package org.arhor.diploma.web.security

import java.io.Serializable

interface AuthToken : Serializable {

    /**
     * Raw token representation.
     */
    val token: String

    /**
     * Identifier of the token owner entity.
     */
    val ownerId: Long

    /**
     * Owner authorities list.
     */
    val authorities: List<String>
}
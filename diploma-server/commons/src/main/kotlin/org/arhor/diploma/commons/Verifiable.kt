package org.arhor.diploma.commons

interface Verifiable : Prioritized<Verifiable> {

    fun verify(): ActionResult<String>
}
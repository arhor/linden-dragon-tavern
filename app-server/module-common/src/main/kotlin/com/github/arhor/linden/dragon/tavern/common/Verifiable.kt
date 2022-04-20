package com.github.arhor.linden.dragon.tavern.common

interface Verifiable : Prioritized<Verifiable> {

    fun verify(): Result<String>
}

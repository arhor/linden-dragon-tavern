package com.github.arhor.linden.dragon.tavern.service

interface StringSanitizer {

    fun sanitize(input: String?): String?
}

package org.arhor.diploma.service.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import org.arhor.diploma.core.Identifiable

data class Monster(
    val name: String,
    val size: String,
    val type: String,
    val challengeRating: Double
) : Identifiable<String> {

    @JsonIgnore
    override fun getId(): String? {
        return name
    }
}
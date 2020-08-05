package org.arhor.diploma.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class CharacterClass(
    override var id: Long?,
    var name: String?,
    var hitDie: String?
) : DTO<Long>

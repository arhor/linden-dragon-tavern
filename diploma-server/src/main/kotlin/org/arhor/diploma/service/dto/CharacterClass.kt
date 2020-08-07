package org.arhor.diploma.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.arhor.diploma.core.Identifiable

@JsonIgnoreProperties(ignoreUnknown = true)
data class CharacterClass(
    override var id: Long?,
    var name: String?,
    var hitDie: String?
) : Identifiable<Long>

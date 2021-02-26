package org.arhor.diploma.service.dto

import org.arhor.diploma.commons.Identifiable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class AccountDTO(
    override var id: Long? = null,

    @field:Pattern(regexp = "^$")
    var username: String? = null,

    @field:NotBlank
    @field:NotNull
    var password: String? = null,

    @field:NotBlank
    @field:NotNull
    var email: String? = null,

    @field:NotBlank
    @field:NotNull
    var firstName: String? = null,

    @field:NotBlank
    @field:NotNull
    var lastName: String? = null
) : Identifiable<Long>

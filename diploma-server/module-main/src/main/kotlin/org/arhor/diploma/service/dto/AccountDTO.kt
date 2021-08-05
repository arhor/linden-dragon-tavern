package org.arhor.diploma.service.dto

import org.arhor.diploma.commons.Identifiable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class AccountDTO(

    override var id: Long? = null,

    @field:NotBlank
    var username: String? = null,

    @field:NotBlank
    var password: String? = null,

    @field:NotBlank
    var email: String? = null,

    var firstName: String? = null,

    var lastName: String? = null,

) : Identifiable<Long>

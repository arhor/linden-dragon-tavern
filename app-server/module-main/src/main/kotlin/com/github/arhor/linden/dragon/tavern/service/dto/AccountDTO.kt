package com.github.arhor.linden.dragon.tavern.service.dto

import com.github.arhor.linden.dragon.tavern.common.Identifiable
import javax.validation.constraints.NotBlank

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

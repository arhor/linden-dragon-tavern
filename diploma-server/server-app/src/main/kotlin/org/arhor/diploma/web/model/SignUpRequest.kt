package org.arhor.diploma.web.model

data class SignUpRequest(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String
)

package chistousov.ilya.password_details.domain.entity

data class PasswordModel(
    val id: Int = 0,
    val title: String,
    val password: String,
    val login: String,
    val email: String,
    val url: String
)
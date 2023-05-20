package chistousov.ilya.passwordkeeper.domain.model

data class PasswordModel (
    val id: Int,
    val title: String,
    val password: String,
    val login: String?,
    val email: String?,
    val url: String?,
//    val image: String
)
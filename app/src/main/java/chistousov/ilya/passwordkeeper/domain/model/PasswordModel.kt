package chistousov.ilya.passwordkeeper.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class PasswordModel (
    val id: Int = 0,
    val title: String,
    val password: String,
    val login: String,
    val email: String,
    val url: String,
)
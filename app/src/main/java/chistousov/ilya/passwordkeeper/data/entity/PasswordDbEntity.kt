package chistousov.ilya.passwordkeeper.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwords")
data class PasswordDbEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val password: String,
    val login: String,
    val email: String,
    val url: String,
)

package chistousov.ilya.passwordkeeper.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserDbEntity(
    @PrimaryKey
    val password: String
)
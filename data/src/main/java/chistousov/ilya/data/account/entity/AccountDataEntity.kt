package chistousov.ilya.data.account.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class AccountDataEntity(
    @PrimaryKey
    val password: String
)
package chistousov.ilya.data.password.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwords")
data class PasswordDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
//    @ColumnInfo(name = "password", typeAffinity = ColumnInfo.BLOB)
    val password: String,
    val login: String,
    val email: String,
    val url: String,
)
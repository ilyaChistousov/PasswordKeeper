package chistousov.ilya.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import chistousov.ilya.data.account.dao.AccountDao
import chistousov.ilya.data.account.entity.AccountDataEntity
import chistousov.ilya.data.password.dao.PasswordDao
import chistousov.ilya.data.password.entity.PasswordDataEntity

@Database(entities = [PasswordDataEntity::class, AccountDataEntity::class], version = 1)
abstract class PasswordDb : RoomDatabase() {

    abstract fun getPasswordDao(): PasswordDao
    abstract fun getUserDao(): AccountDao
}
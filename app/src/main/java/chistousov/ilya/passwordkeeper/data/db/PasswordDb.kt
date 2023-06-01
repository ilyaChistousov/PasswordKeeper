package chistousov.ilya.passwordkeeper.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import chistousov.ilya.passwordkeeper.data.dao.PasswordDao
import chistousov.ilya.passwordkeeper.data.dao.UserDao
import chistousov.ilya.passwordkeeper.data.entity.PasswordDbEntity
import chistousov.ilya.passwordkeeper.data.entity.UserDbEntity
import javax.inject.Singleton

@Database(entities = [PasswordDbEntity::class, UserDbEntity::class], version = 1)
abstract class PasswordDb : RoomDatabase() {

    abstract fun getPasswordDao(): PasswordDao
    abstract fun getUserDao(): UserDao
}
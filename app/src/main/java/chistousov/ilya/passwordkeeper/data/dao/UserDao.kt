package chistousov.ilya.passwordkeeper.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import chistousov.ilya.passwordkeeper.data.entity.UserDbEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createUser(user: UserDbEntity)

    @Query("SELECT * FROM user WHERE password = :password")
    suspend fun getUser(password: String): UserDbEntity

    @Query("SELECT * FROM user")
    suspend fun isRegistered(): UserDbEntity?

    @Query("DELETE FROM user")
    suspend fun deleteUser()
}
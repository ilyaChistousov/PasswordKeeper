package chistousov.ilya.data.account.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import chistousov.ilya.data.account.entity.AccountDataEntity

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createAccount(user: AccountDataEntity)

    @Query("SELECT * FROM account WHERE password = :password")
    suspend fun getAccount(password: String): AccountDataEntity

    @Query("SELECT * FROM account")
    suspend fun isRegistered(): AccountDataEntity?

    @Query("DELETE FROM account")
    suspend fun deleteUser()
}
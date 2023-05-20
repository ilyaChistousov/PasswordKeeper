package chistousov.ilya.passwordkeeper.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import chistousov.ilya.passwordkeeper.data.entity.PasswordDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {

    @Query("SELECT * FROM passwords")
    fun getPasswordList() : Flow<List<PasswordDbEntity>>

    @Query("SELECT * FROM passwords WHERE id = :passwordId LIMIT 1")
    suspend fun getPassword(passwordId: Int) : PasswordDbEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPassword(password: PasswordDbEntity)

    @Query("DELETE FROM passwords WHERE id = :passwordId")
    suspend fun deletePassword(passwordId: Int)

}
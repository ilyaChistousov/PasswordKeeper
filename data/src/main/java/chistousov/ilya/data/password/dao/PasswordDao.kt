package chistousov.ilya.data.password.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import chistousov.ilya.data.password.entity.PasswordDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {
    @Query("SELECT * FROM passwords")
    fun getPasswordList() : Flow<List<PasswordDataEntity>>

    @Query("SELECT * FROM passwords WHERE id = :passwordId LIMIT 1")
    suspend fun getPassword(passwordId: Int) : PasswordDataEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPassword(password: PasswordDataEntity)

    @Query("DELETE FROM passwords WHERE id = :passwordId")
    suspend fun deletePassword(passwordId: Int)

    @Query("SELECT * FROM passwords WHERE title LIKE '%' || :query || '%'")
    fun searchPassword(query: String) : Flow<List<PasswordDataEntity>>

}
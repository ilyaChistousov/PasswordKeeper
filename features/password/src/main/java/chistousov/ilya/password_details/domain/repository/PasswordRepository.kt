package chistousov.ilya.password_details.domain.repository

import chistousov.ilya.password_details.domain.entity.PasswordModel
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {

    fun getAllPasswords(): Flow<List<PasswordModel>>

    fun searchPassword(query: String): Flow<List<PasswordModel>>

    suspend fun getPassword(passwordId: Int): PasswordModel

    suspend fun deletePassword(id: Int)

    suspend fun createPassword(passwordModel: PasswordModel)

    suspend fun updatePassword(passwordModel: PasswordModel)
}
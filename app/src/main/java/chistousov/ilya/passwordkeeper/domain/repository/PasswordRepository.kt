package chistousov.ilya.passwordkeeper.domain.repository

import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {

    suspend fun getPassword(passwordId: Int) : PasswordModel

    fun getListPassword() : Flow<List<PasswordModel>>

    suspend fun createPassword(passwordModel: PasswordModel)

    suspend fun updatePassword(passwordModel: PasswordModel)

    suspend fun deletePassword(passwordId: Int)

}
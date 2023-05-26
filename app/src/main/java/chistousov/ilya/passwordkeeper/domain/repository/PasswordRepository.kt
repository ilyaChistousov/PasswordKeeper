package chistousov.ilya.passwordkeeper.domain.repository

import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.utils.UiState
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {

    suspend fun getPassword(passwordId: Int) : Flow<UiState<PasswordModel>>

    fun getListPassword() : Flow<UiState<List<PasswordModel>>>

    suspend fun createPassword(passwordModel: PasswordModel)

    suspend fun updatePassword(passwordModel: PasswordModel)

    suspend fun deletePassword(passwordId: Int)

    suspend fun searchPassword(query: String) : Flow<UiState<List<PasswordModel>>>

}
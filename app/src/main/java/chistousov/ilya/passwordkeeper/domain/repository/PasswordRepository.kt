package chistousov.ilya.passwordkeeper.domain.repository

import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.presentation.utils.UiState
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {

    suspend fun getPassword(passwordId: Int) : PasswordModel

    suspend fun getListPassword() : List<PasswordModel>

    suspend fun createPassword(passwordModel: PasswordModel)

    suspend fun updatePassword(passwordModel: PasswordModel)

    suspend fun deletePassword(passwordId: Int)

    suspend fun searchPassword(query: String) : List<PasswordModel>
}
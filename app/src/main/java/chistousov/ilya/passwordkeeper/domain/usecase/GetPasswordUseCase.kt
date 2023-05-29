package chistousov.ilya.passwordkeeper.domain.usecase

import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.domain.Result
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository
import javax.inject.Inject

class GetPasswordUseCase @Inject constructor(private val repository: PasswordRepository) {

    suspend operator fun invoke(passwordId: Int): Result<PasswordModel> {
        return try {
            Result.Success(repository.getPassword(passwordId))
        } catch (e: Exception) {
            Result.Error(R.string.error_loading_failed)
        }
    }
}
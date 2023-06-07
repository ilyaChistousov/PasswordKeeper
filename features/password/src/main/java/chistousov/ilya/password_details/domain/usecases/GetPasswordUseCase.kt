package chistousov.ilya.password_details.domain.usecases

import chistousov.ilya.common.Result
import chistousov.ilya.password_details.domain.entity.PasswordModel
import chistousov.ilya.password_details.domain.repository.PasswordRepository
import javax.inject.Inject

class GetPasswordUseCase @Inject constructor(private val repository: PasswordRepository) {

    suspend operator fun invoke(passwordId: Int): Result<PasswordModel> {
        return try {
            Result.Success(repository.getPassword(passwordId))
        } catch (e: Exception) {
            Result.Error("Ошибка загрузки данных")
        }
    }
}
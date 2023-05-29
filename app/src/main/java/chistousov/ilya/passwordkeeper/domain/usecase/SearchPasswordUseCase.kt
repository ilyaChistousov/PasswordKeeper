package chistousov.ilya.passwordkeeper.domain.usecase

import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.domain.Result
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchPasswordUseCase @Inject constructor(private val repository: PasswordRepository) {

    suspend operator fun invoke(query: String): Flow<Result<List<PasswordModel>>> = flow {
        try {
            emit(Result.Success(repository.searchPassword(query)))
        } catch (e: Exception) {
            emit(Result.Error(R.string.password_error))
        }
    }
}
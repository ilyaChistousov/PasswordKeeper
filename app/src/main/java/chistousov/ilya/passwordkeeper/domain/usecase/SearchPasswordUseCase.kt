package chistousov.ilya.passwordkeeper.domain.usecase

import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.domain.Result
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchPasswordUseCase @Inject constructor(private val repository: PasswordRepository) {

    suspend operator fun invoke(query: String): Flow<Result<List<PasswordModel>>> = flow {
        try {
            repository.searchPassword(query).collect {
                emit(Result.Success(it))
            }
        } catch (e: Exception) {
            emit(Result.Error("Ошибка загрузки данных"))
        }
    }
}
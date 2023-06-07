package chistousov.ilya.password_details.domain.usecases

import chistousov.ilya.common.Result
import chistousov.ilya.password_details.domain.entity.PasswordModel
import chistousov.ilya.password_details.domain.repository.PasswordRepository
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
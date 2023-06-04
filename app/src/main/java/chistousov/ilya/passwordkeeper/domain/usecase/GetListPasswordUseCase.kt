package chistousov.ilya.passwordkeeper.domain.usecase

import chistousov.ilya.passwordkeeper.domain.Result
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetListPasswordUseCase @Inject constructor (private val repository: PasswordRepository) {

    suspend operator fun invoke(): Flow<Result<List<PasswordModel>>> = flow{
        try {
            repository.getListPassword().collect {
                emit(Result.Success(it))
            }
        } catch (e: Exception) {
            emit(Result.Error("Ошибка загрузки данных"))
        }
    }
}
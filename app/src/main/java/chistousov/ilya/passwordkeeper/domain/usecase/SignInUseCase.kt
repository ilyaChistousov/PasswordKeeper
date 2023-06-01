package chistousov.ilya.passwordkeeper.domain.usecase

import chistousov.ilya.passwordkeeper.domain.Result
import chistousov.ilya.passwordkeeper.domain.exception.UserNotFoundException
import chistousov.ilya.passwordkeeper.domain.model.UserModel
import chistousov.ilya.passwordkeeper.domain.repository.UserRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val repository: UserRepository){

    suspend operator fun invoke(password: String): Result<Boolean> {
        return try {
            repository.getUser(password)
            Result.Success(true)
        } catch (e: UserNotFoundException) {
            Result.Error(e.message)
        }
    }
}
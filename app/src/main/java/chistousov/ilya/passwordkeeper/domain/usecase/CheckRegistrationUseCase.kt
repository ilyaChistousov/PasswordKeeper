package chistousov.ilya.passwordkeeper.domain.usecase

import chistousov.ilya.passwordkeeper.domain.Result
import chistousov.ilya.passwordkeeper.domain.repository.UserRepository
import javax.inject.Inject

class CheckRegistrationUseCase @Inject constructor(private val userRepository: UserRepository){

    suspend operator fun invoke() : Result<Boolean> {
        val result = userRepository.isRegistered()
        return Result.Success(result)
    }
}
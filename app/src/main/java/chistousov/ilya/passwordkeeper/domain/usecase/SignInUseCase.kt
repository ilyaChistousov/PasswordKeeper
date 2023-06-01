package chistousov.ilya.passwordkeeper.domain.usecase

import chistousov.ilya.passwordkeeper.domain.repository.UserRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val repository: UserRepository){

    suspend operator fun invoke(password: String) = repository.signIn(password)
}
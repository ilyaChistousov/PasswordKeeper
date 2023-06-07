package chistousov.ilya.sign_in.domain.usecase

import chistousov.ilya.sign_in.domain.repository.SignInRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val repository: SignInRepository){
    suspend operator fun invoke(password: String) = repository.signIn(password)
}
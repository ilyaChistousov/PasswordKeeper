package chistousov.ilya.sign_in.domain.usecase

import chistousov.ilya.sign_in.domain.repository.SignInRepository
import javax.inject.Inject

class IsSignedUpUseCase @Inject constructor(private val repository: SignInRepository) {

    suspend operator fun invoke() = repository.isSignedUp()
}
package chistousov.ilya.sign_up.domain.usecases

import chistousov.ilya.sign_up.domain.repository.SignUpRepository
import javax.inject.Inject

class IsSignedUpUseCase @Inject constructor(private val repository: SignUpRepository) {

    suspend operator fun invoke(): Boolean = repository.isSignedUp()
}
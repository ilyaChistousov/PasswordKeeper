package chistousov.ilya.passwordkeeper.domain.usecase

import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository
import javax.inject.Inject

class GetPasswordUseCase @Inject constructor (private val repository: PasswordRepository) {

    suspend operator fun invoke(passwordId: Int) = repository.getPassword(passwordId)
}
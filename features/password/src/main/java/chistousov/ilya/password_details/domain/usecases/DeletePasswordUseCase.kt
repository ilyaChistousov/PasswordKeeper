package chistousov.ilya.password_details.domain.usecases

import chistousov.ilya.password_details.domain.repository.PasswordRepository
import javax.inject.Inject

class DeletePasswordUseCase @Inject constructor(private val repository: PasswordRepository) {

    suspend operator fun invoke(passwordId: Int) = repository.deletePassword(passwordId)
}
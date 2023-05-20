package chistousov.ilya.passwordkeeper.domain.usecase

import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository

class DeletePasswordUseCase (private val repository: PasswordRepository) {

    suspend operator fun invoke(passwordId: Int) = repository.deletePassword(passwordId)
}
package chistousov.ilya.passwordkeeper.domain.usecase

import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository

class CreatePasswordUseCase (private val repository: PasswordRepository) {

    suspend operator fun invoke(passwordModel: PasswordModel) =
        repository.createPassword(passwordModel)
}
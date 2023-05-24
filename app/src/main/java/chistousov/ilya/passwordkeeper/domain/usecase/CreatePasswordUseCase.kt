package chistousov.ilya.passwordkeeper.domain.usecase

import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository
import javax.inject.Inject

class CreatePasswordUseCase @Inject constructor(private val repository: PasswordRepository) {

    suspend operator fun invoke(passwordModel: PasswordModel) =
        repository.createPassword(passwordModel)
}
package chistousov.ilya.passwordkeeper.domain.usecase

import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository

class UpdatePasswordUseCase (private val repository: PasswordRepository){

    suspend operator fun invoke (passwordModel: PasswordModel) =
        repository.updatePassword(passwordModel)
}
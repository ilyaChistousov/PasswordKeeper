package chistousov.ilya.passwordkeeper.domain.usecase

import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository
import javax.inject.Inject

class UpdatePasswordUseCase @Inject constructor(private val repository: PasswordRepository) {

    suspend operator fun invoke(
        id: Int, title: String, password: String, login: String, email: String, url: String
    ) {
        val updatedPassword = PasswordModel(id, title, password, login, email, url)
        repository.updatePassword(updatedPassword)
    }
}
package chistousov.ilya.password_details.domain.usecases

import chistousov.ilya.password_details.domain.entity.PasswordField
import chistousov.ilya.password_details.domain.entity.PasswordModel
import chistousov.ilya.password_details.domain.exceptions.EmptyFieldException
import chistousov.ilya.password_details.domain.repository.PasswordRepository
import javax.inject.Inject

class UpdatePasswordUseCase @Inject constructor(private val repository: PasswordRepository) {

    suspend operator fun invoke(
        id: Int, title: String, password: String, login: String, email: String, url: String
    ) {
        if (title.isBlank()) throw EmptyFieldException(PasswordField.TITLE)
        if (password.isBlank()) throw EmptyFieldException(PasswordField.PASSWORD)
        val updatedPasswordModel = PasswordModel(id, title, password, login, email, url)
        repository.updatePassword(updatedPasswordModel)
    }
}
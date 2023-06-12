package chistousov.ilya.password_details.domain.usecases

import chistousov.ilya.password_details.domain.entity.PasswordField
import chistousov.ilya.password_details.domain.entity.PasswordModel
import chistousov.ilya.password_details.domain.exceptions.EmptyFieldException
import chistousov.ilya.password_details.domain.repository.PasswordRepository
import javax.inject.Inject

class CreatePasswordUseCase @Inject constructor(private val repository: PasswordRepository) {

    suspend operator fun invoke(
        title: String, password: String, login: String, email: String, url: String
    ) {
        if (title.isBlank()) throw EmptyFieldException(PasswordField.TITLE)
        if (password.isBlank()) throw EmptyFieldException(PasswordField.PASSWORD)
        val updatedPasswordModel = PasswordModel(
            title = title,
            password = password,
            login = login,
            email = email,
            url = url
        )
        repository.createPassword(updatedPasswordModel)
    }
}
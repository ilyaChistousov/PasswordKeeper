package chistousov.ilya.password_details.domain.usecases

import chistousov.ilya.password_details.domain.entity.PasswordModel
import chistousov.ilya.password_details.domain.exceptions.EmptyPasswordException
import chistousov.ilya.password_details.domain.exceptions.EmptyTitleException
import chistousov.ilya.password_details.domain.repository.PasswordRepository
import javax.inject.Inject

class CreatePasswordUseCase @Inject constructor(private val repository: PasswordRepository) {

    suspend operator fun invoke(
        title: String, password: String, login: String, email: String, url: String
    ) {
        if (title.isBlank()) throw EmptyTitleException()
        if (password.isBlank()) throw EmptyPasswordException()
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
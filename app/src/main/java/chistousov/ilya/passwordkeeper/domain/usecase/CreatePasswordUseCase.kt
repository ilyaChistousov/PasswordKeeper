package chistousov.ilya.passwordkeeper.domain.usecase

import chistousov.ilya.passwordkeeper.domain.exception.ValidationException
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository
import chistousov.ilya.passwordkeeper.presentation.utils.Validator
import javax.inject.Inject

class CreatePasswordUseCase @Inject constructor(private val repository: PasswordRepository) {

    suspend operator fun invoke(
        title: String, password: String, login: String, email: String, url: String
    ) {
        val validator = Validator()

        val validationMap = mapOf(Validator.TITLE_KEY to title, Validator.PASSWORD_KEY to password)
            .mapValues { validator.validate(it.value) }

        if (validationMap.values.all { it == null }) {
            val newPassword = PasswordModel(
                title = title,
                password = password,
                login = login,
                email = email,
                url = url
            )
            repository.createPassword(newPassword)
        } else throw ValidationException(validationMap)
    }
}
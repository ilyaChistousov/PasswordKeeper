package chistousov.ilya.passwordkeeper.domain.usecase

import chistousov.ilya.passwordkeeper.domain.exception.ValidationException
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository
import chistousov.ilya.passwordkeeper.presentation.utils.Validator
import javax.inject.Inject

class UpdatePasswordUseCase @Inject constructor(private val repository: PasswordRepository) {

    suspend operator fun invoke(
        id: Int, title: String, password: String, login: String, email: String, url: String
    ) {
        val validator = Validator()

        val validationMap = mapOf(Validator.TITLE_KEY to title, Validator.PASSWORD_KEY to password)
            .mapValues { validator.validate(it.value) }

        if (validationMap.values.all { it == null }) {
            val updatedPassword = PasswordModel(
                id,
                title,
                password,
                login,
                email,
                url
            )
            repository.updatePassword(updatedPassword)
        } else throw ValidationException(validationMap)
    }
}
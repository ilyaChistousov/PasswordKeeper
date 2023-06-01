package chistousov.ilya.passwordkeeper.domain.usecase

import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.domain.exception.ValidationException
import chistousov.ilya.passwordkeeper.domain.model.UserModel
import chistousov.ilya.passwordkeeper.domain.repository.UserRepository
import chistousov.ilya.passwordkeeper.presentation.utils.Validator
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(password: String, confirmPassword: String) {
        val passwordValidator = Validator().apply {
            addRule(Validator.minimumLength(6, R.string.min_char_field))
            addRule(Validator.maximumLength(16, R.string.max_char_field))
        }
        val confirmPasswordValidator = Validator().apply {
            addRule(Validator.sameText(password, R.string.not_same_field))
        }

        val validatedPassword = passwordValidator.validate(password)
        val validateConfirmPassword = confirmPasswordValidator.validate(confirmPassword)
        val validationMap = mapOf(
            Validator.PASSWORD_KEY to validatedPassword,
            Validator.CONFIRM_PASSWORD_KEY to validateConfirmPassword
        )

        if (validationMap.values.all { it == null }) {
            repository.createUser(UserModel(password))
        } else throw ValidationException(validationMap)
    }
}
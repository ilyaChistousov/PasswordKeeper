package chistousov.ilya.sign_up.domain.usecases

import chistousov.ilya.common.RequiredFieldException
import chistousov.ilya.sign_up.domain.entity.AccountModel
import chistousov.ilya.sign_up.domain.exceptions.MaxFieldLengthException
import chistousov.ilya.sign_up.domain.exceptions.MinFieldLengthException
import chistousov.ilya.sign_up.domain.exceptions.NotSameFieldsException
import chistousov.ilya.sign_up.domain.repository.SignUpRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: SignUpRepository) {

    suspend operator fun invoke(password: String, confirmPassword: String) {
        if (password.isBlank()) throw RequiredFieldException()
        if (confirmPassword != password) throw NotSameFieldsException()
        if (password.length < 6) throw MinFieldLengthException()
        if (password.length > 16) throw MaxFieldLengthException()

        val newAccount = AccountModel(password)
        repository.signUp(newAccount)
    }
}
package chistousov.ilya.password_details.presentation.generate_password

import chistousov.ilya.password_details.domain.usecases.GeneratePasswordUseCase
import chistousov.ilya.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GeneratePasswordViewModel @Inject constructor(
    private val generateUniquePasswordUseCase: GeneratePasswordUseCase
) : BaseViewModel() {

    val generatedPassword = flowValue(State())

    init {
        generatedPassword.value = State(
            generateUniquePasswordUseCase(
                5,
                withDigits = true,
                withUppercase = true,
                withSpecial = true
            )
        )
    }

    fun generatePassword(
        length: Int,
        withDigit: Boolean,
        withUpperCase: Boolean,
        withSpecialChar: Boolean
    ) {
        generatedPassword.value = State(
            generateUniquePasswordUseCase(
                length,
                withDigit,
                withUpperCase,
                withSpecialChar
            )
        )
    }

    data class State(
        val generatedPassword: String = ""
    )
}
package chistousov.ilya.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.ViewModel
import chistousov.ilya.passwordkeeper.domain.usecase.GenerateUniquePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GeneratePasswordViewModel @Inject constructor(
    private val generateUniquePasswordUseCase: GenerateUniquePasswordUseCase
) : ViewModel() {

    private val _generatedPassword = MutableStateFlow("")
    val generatedPassword: StateFlow<String> = _generatedPassword.asStateFlow()

    init {
        _generatedPassword.value = generateUniquePasswordUseCase(
            5,
            withDigits = true,
            withUppercase = true,
            withSpecial = true
        )
    }

    fun generatePassword(
        length: Int,
        withDigit: Boolean,
        withUpperCase: Boolean,
        withSpecialChar: Boolean
    ) {
        _generatedPassword.value = generateUniquePasswordUseCase(
            length,
            withDigit,
            withUpperCase,
            withSpecialChar
        )
    }
}
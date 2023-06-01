package chistousov.ilya.passwordkeeper.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.domain.usecase.CreateUserUseCase
import chistousov.ilya.passwordkeeper.presentation.utils.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private val _passwordValidation = MutableStateFlow<Map<String, Int?>>(mutableMapOf())
    val passwordValidation: StateFlow<Map<String, Int?>> = _passwordValidation.asStateFlow()

    fun signUp(password: String, confirmPassword: String, onSuccess: () -> Unit) {
        validateInput(password, confirmPassword)
        if (_passwordValidation.value.entries.all { it.value == null }) {
            viewModelScope.launch {
                createUserUseCase(password)
                onSuccess()
                Log.d("AAAAAAAA", "Удачно")
            }
        }
    }

    private fun validateInput(password: String, confirmPassword: String) {
        val passwordValidator = Validator().apply {
            addRule(Validator.minimumLength(6, R.string.min_char_field))
            addRule(Validator.maximumLength(16, R.string.max_char_field))
        }
        val passwordConfirmValidator = Validator().apply {
            addRule(Validator.sameText(password, R.string.not_same_field))
        }

        val validatedPassword = passwordValidator.validate(password)
        val validateConfirmPassword = passwordConfirmValidator.validate(confirmPassword)

        _passwordValidation.value = mapOf(
            Validator.PASSWORD_KEY to validatedPassword,
            Validator.CONFIRM_PASSWORD_KEY to validateConfirmPassword
        )
    }
}

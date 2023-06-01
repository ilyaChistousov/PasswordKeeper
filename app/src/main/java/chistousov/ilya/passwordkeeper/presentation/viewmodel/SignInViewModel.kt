package chistousov.ilya.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.domain.usecase.SignInUseCase
import chistousov.ilya.passwordkeeper.presentation.utils.UiState
import chistousov.ilya.passwordkeeper.presentation.utils.Validator
import chistousov.ilya.passwordkeeper.presentation.utils.mapToUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _passwordValidation = MutableStateFlow<Map<String, Int?>>(mutableMapOf())
    val passwordValidation: StateFlow<Map<String, Int?>> = _passwordValidation.asStateFlow()

    private val _isSignedIn = MutableStateFlow<UiState<Boolean>>(UiState.Loading())
    val isSignedIn : StateFlow<UiState<Boolean>> = _isSignedIn.asStateFlow()

    fun signIn(password: String, onSuccess: () -> Unit) = viewModelScope.launch {
        validationField(password)
        if (_passwordValidation.value.values.all { it == null }) {
           _isSignedIn.value = signInUseCase(password).mapToUiState()
           onSuccess()
        }
    }

    private fun validationField(password: String) {
        val passwordValidator = Validator().apply {
            addRule(Validator.minimumLength(6, R.string.min_char_field))
            addRule(Validator.maximumLength(16, R.string.max_char_field))
        }

        val validatingPassword = passwordValidator.validate(password)

        _passwordValidation.value = mapOf(Validator.PASSWORD_KEY to validatingPassword)
    }
}